# Add these additional variables
variable "ecs_ami_id" {
  description = "The Amazon ECS-optimized AMI ID for your region"
  type        = string
  default = "ami-0974f0859e646c32d"
}

variable "container_port" {
  description = "Port exposed by the container"
  type        = number
  default     = 8080
}

variable "aws_region" {
  description = "The AWS region to deploy to"
  type        = string
  default     = "us-east-1"
}

variable "app_name" {
  description = "Name of the application"
  type        = string
  default     = "elimapass"
}

variable "health_check_path" {
  description = "Path for ALB health check"
  type        = string
  default     = "/elimapass/v1/actuator/health"
}

# Add these for the database
variable "db_username" {
  description = "Username for the PostgreSQL database"
  type        = string
  sensitive   = true
}

variable "db_password" {
  description = "Password for the PostgreSQL database"
  type        = string
  sensitive   = true
}

variable "db_name" {
  description = "Name of the PostgreSQL database"
  type        = string
  default     = "elimapass"
}