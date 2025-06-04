resource "aws_ecr_repository" "existing" {
  name                 = "elimapass-spring"

  lifecycle {
    prevent_destroy = true
  }
}