workflow "ultraman rpc workflow" {
  on = "push"
  resolves = ["docker://maven"]
}

action "tag-filter" {
  uses = "actions/bin/filter@707718ee26483624de00bd146e073d915139a3d8"
  runs = "actions/bin/filter@master"
  args = "tag"
}

action "docker://maven" {
  uses = "docker://maven"
  runs = "mvn clean test"
  needs = ["tag-filter"]
}
