# Define targets as functions
function test {
    mvn test
    if ($LastExitCode -ne 0) {
        Write-Host "Tests failed. Aborting deployment."
        exit 1
    }
}

function build {
    mvn clean compile jib:build
    if ($LastExitCode -ne 0) {
        Write-Host "Build failed. Aborting deployment."
        exit 1
    }
}

function run {
    docker-compose up -d
}

function stop {
    docker-compose down
}

function deploy {
    test
    build
    run
}

# Check for arguments to decide which function to call
if ($args.Length -eq 0) {
    Write-Host "No arguments provided. Please specify 'deploy' or 'stop'."
} else {
    $action = $args[0]
    if ($action -eq "-deploy") {
        deploy
    } elseif ($action -eq "-stop") {
        stop
    } else {
        Write-Host "Invalid argument. Please specify 'deploy' or 'stop'."
    }
}
