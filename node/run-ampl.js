const child_process = require('child_process');

let cwd = "/home/elipzer/eclipse-workspace/EvacuationPlanner/ampl";

let process = child_process.spawn("ampl", [ "solve.run" ], { cwd: cwd, shell: true });

let stdout = '';
let stderr = '';

process.stdout.on('data', (chunk) => {
    stdout += chunk;
});

process.stderr.on('data', (chunk) => {
    stderr += chunk;
});

process.on('close', (code, signal) => {
    console.log('code', code, 'signal', signal);
    console.log('stdout');
    console.log(stdout);
    
    console.log('stderr');
    console.log(stderr);
});

