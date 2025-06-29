# ALB Configuration (Persistent)
resource "aws_lb" "main" {
  name               = "${var.app_name}-nlb"
  internal           = false
  load_balancer_type = "network"  # Changed from "application" to "network"
  subnets            = [aws_subnet.private_1.id, aws_subnet.private_2.id]

  lifecycle {
    ignore_changes  = [tags]
  }

  tags = {
    Name = "${var.app_name}-nlb"
  }
}

resource "aws_lb_target_group" "app" {
  name        = "${var.app_name}-tg"
  port        = 8080
  protocol    = "TCP"
  vpc_id      = aws_vpc.main.id
  target_type = "instance"

  health_check {
    protocol            = "TCP"
    port                = "traffic-port"
    healthy_threshold   = 3
    unhealthy_threshold = 3
    interval            = 30
  }
}

resource "aws_lb_listener" "tcp" {
  load_balancer_arn = aws_lb.main.arn
  port              = 80
  protocol          = "TCP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.app.arn
  }
}