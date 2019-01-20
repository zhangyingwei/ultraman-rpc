workflow "ultraman rpc workflow" {
  on = "push"
  resolves = ["maven", "mail-to-me"]
}

action "maven" {
  uses = "docker://maven"
  secrets = ["GITHUB_TOKEN"]
  runs = "mvn clean test"
}

action "mail-to-me" {
  uses = "docker://ubuntu"
  runs = "sudo apt-get -y install mailutils"
}
