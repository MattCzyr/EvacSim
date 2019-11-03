const child_process = require('child_process');
const fs = require('fs');
const rp = require('request-promise');

async function run_process(command, args, cwd) { 
    let process = child_process.spawn(command, args, { cwd: cwd });

    let stdout = '';
    let stderr = '';

    process.stdout.on('data', (chunk) => {
        stdout += chunk;
    });

    process.stderr.on('data', (chunk) => {
        stderr += chunk;
    });

    return new Promise((resolve, reject) => {
        process.on('close', (code, signal) => {
            if (code != 0) {
                reject('exit code: ' + code);
            }
            console.log('code', code, 'signal', signal);
            console.log('stdout');
            console.log(stdout);
            
            console.log('stderr');
            console.log(stderr);
            resolve(stdout);
        });
    });
}

async function run() {
    let cwd = null;

    // Pull Data From the Cloud

    // http://s3.us-east.cloud-object-storage.appdomain.cloud/troymodel/edges.csv
    // http://s3.us-east.cloud-object-storage.appdomain.cloud/troymodel/hurricanes.csv
    // http://s3.us-east.cloud-object-storage.appdomain.cloud/troymodel/main.csv
    // http://s3.us-east.cloud-object-storage.appdomain.cloud/troymodel/nodes.csv

    let endpoint = 'http://s3.us-east.cloud-object-storage.appdomain.cloud/troymodel/';

    console.log('downloading edges...');
    let edgesData = await rp(endpoint + 'edges.csv');
    fs.writeFileSync('../models/troy_cloud_model/edges.csv', edgesData);

    console.log('downloading hurricanes...');
    let hurricanesData = await rp(endpoint + 'hurricanes.csv');
    fs.writeFileSync('../models/troy_cloud_model/hurricanes.csv', hurricanesData);

    console.log('downloading main...');
    let mainData = await rp(endpoint + 'main.csv');
    fs.writeFileSync('../models/troy_cloud_model/main.csv', mainData);

    console.log('downloading nodes...');
    let nodesData = await rp(endpoint + 'nodes.csv');
    fs.writeFileSync('../models/troy_cloud_model/nodes.csv', nodesData);

    // launch java (read input data)
    console.log('generating AMPL data')
    cwd = "/home/elipzer/eclipse-workspace/EvacuationPlanner";
    try {
        await run_process("java", [
            "-classpath",
            "/home/elipzer/eclipse-workspace/EvacuationPlanner/bin"
                + ":/home/elipzer/eclipse-workspace/EvacuationPlanner/include/commons-csv-1.7/commons-csv-1.7.jar",
            "evac.AmplPrepMain" ], cwd);    
    } catch (e) {
        console.error(e);
    }
    
    // launch ampl (calculate flows)
    
    console.log('running AMPL calculations')
    cwd = "/home/elipzer/eclipse-workspace/EvacuationPlanner/ampl";
    try {
        let output = await run_process("ampl", [ "ampl_solve.run" ], cwd);
        let lines = output.split('\n');
        let semicolon = lines.indexOf(';');
        let flows = lines.slice(3, semicolon);
        let flowsCsv = flows.map(line => {
            return line.replace(/\s+/g, ',');
        }).join('\n');
        fs.writeFileSync('../data/flows.csv', flowsCsv);
        console.log('output (flows.csv):');
        console.log(flowsCsv);
    } catch (e) {
        console.error(e);
    }

    // launch java (generate kml)
    console.log('generating KML');
    cwd = "/home/elipzer/eclipse-workspace/EvacuationPlanner";
    try {
        await run_process("java", [
            "-classpath",
            "/home/elipzer/eclipse-workspace/EvacuationPlanner/bin"
                + ":/home/elipzer/eclipse-workspace/EvacuationPlanner/include/commons-csv-1.7/commons-csv-1.7.jar",
            "evac.KmlMain" ], cwd);    
    } catch (e) {
        console.error(e);
    }

}

run();

