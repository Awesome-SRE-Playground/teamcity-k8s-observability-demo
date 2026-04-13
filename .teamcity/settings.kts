import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2025.11"

project {

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    params {
        param("env.DOCKER_USERNAME", "alvishpatelhti")
        password("env.DOCKER_PASSWORD", "credentialsJSON:ea1ebc17-66a4-4b41-a0a0-4a011b4b2d8b")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            name = "Install Dependencies"
            id = "Install_Dependencies"
            scriptContent = """
                set -e
                cd app
                npm install
            """.trimIndent()
        }
        script {
            name = "Build Docker Image"
            id = "Build_Docker_Image"
            scriptContent = "docker build -t ${'$'}DOCKER_USERNAME/devops-demo-app:%build.number% ./app"
        }
        script {
            name = "Docker Login"
            id = "Docker_Login"
            scriptContent = """echo "${'$'}DOCKER_PASSWORD" | docker login -u "${'$'}DOCKER_USERNAME" --password-stdin"""
        }
        script {
            name = "Push Image"
            id = "Push_Image"
            scriptContent = "docker push ${'$'}DOCKER_USERNAME/devops-demo-app:%build.number%"
        }
        script {
            name = "Deploy via Helm"
            id = "Deploy_via_Helm"
            scriptContent = """
                helm upgrade --install devops-demo ./devops-demo-chart \
                  -n demo-app \
                  --set image.tag=%build.number%
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})
