workflow "New workflow" {
  on = "push"
  resolves = ["maven"]
}

action "maven" {
  uses = "docker://maven"
  secrets = ["GITHUB_TOKEN"]
  runs = "mvn clean test"
}
