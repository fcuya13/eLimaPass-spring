# API GW Outputs
output "api_gateway_invoke_url" {
  description = "The URL to invoke the API Gateway endpoint"
  value       = aws_api_gateway_deployment.this.invoke_url
}

# Database Outputs
output "rds_endpoint" {
  description = "The connection endpoint for the PostgreSQL database"
  value       = aws_db_instance.postgres.endpoint
}