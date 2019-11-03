const child_process = require('child_process');

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
            console.log('code', code, 'signal', signal);
            console.log('stdout');
            console.log(stdout);
            
            console.log('stderr');
            console.log(stderr);
            resolve();
        });
    });
}

async function run() {
    let cwd = null;

    // launch java (read input data)
    
    cwd = "/home/elipzer/eclipse-workspace/EvacuationPlanner";
    await run_process("java", [ "-classpath", "/home/elipzer/eclipse-workspace/EvacuationPlanner/bin", "evac.KmlTest" ], cwd);
    
    // launch ampl (calculate flows)
    
    cwd = "/home/elipzer/eclipse-workspace/EvacuationPlanner/ampl";
    await run_process("ampl", [ "solve.run" ], cwd);

    // launch java (generate kml)


}

run();

