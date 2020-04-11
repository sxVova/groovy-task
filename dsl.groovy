def giturl = 'https://github.com/quidryan/aws1-sdk-test.git'
for(i in 0..0){
    job("DSL-Yutorial-1-Test-${i}"){
        scm {
            git(giturl)
        }
        steps {
            maven("test -Dtest.suite=${i}")
        }
    }
}

def branches  = ['master','features']

branches.each {branch ->
  job("jenkins-${branch}"){
        scm{
            github('jenkins/jendsl',branch)
        }
    }
}

def createMavenJob(def jobFactory, def name){
  jobFactory.mavenJob(name){
    goals('clean')
  }
}

def jobA = createMavenJob(this, 'project-a')
def jobB = createMavenJob(this, 'project-b')

jobB.with{
    scm {
        github('example-jenkins/project-a')
    }
}

jenkins.model.Jenkins.theInstance.getProjects().each { job ->
    if (!job.name.contains('project-')) {
        println job.name
        job.delete()
    }
}
