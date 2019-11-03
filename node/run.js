const child_process = require('child_process');
const fs = require('fs');

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

    // launch java (read input data)
    
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

