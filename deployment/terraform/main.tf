terraform {
  required_version = ">= 1.5.7"
  required_providers {
    stackit = {
      source  = "stackitcloud/stackit"
      version = "0.23.0"
    }
  }
}

provider "stackit" {
  region                   = "eu01"
  enable_beta_resources    = true
  service_account_key_path = "private.pem"
}

resource "stackit_argus_instance" "yatt_argus" {
  project_id = var.project_id
  name       = "yatt"
  plan_name  = "Observability-Starter-EU01"
}

resource "stackit_dns_zone" "yatt_dns_zone" {
  project_id    = var.project_id
  name          = "yatt"
  dns_name      = "yatt.stackit.run"
  contact_email = "lars.duelfer@mail.schwarz"
  type          = "primary"
}

resource "stackit_dns_record_set" "yatt_dns_record_set_app" {
  depends_on = [stackit_dns_zone.yatt_dns_zone]
  project_id = var.project_id
  zone_id    = stackit_dns_zone.yatt_dns_zone.zone_id
  name       = "app"
  type       = "A"
  records    = [var.public_ip]
}

resource "stackit_dns_record_set" "yatt_dns_record_set_admin" {
  depends_on = [stackit_dns_zone.yatt_dns_zone]
  project_id = var.project_id
  zone_id    = stackit_dns_zone.yatt_dns_zone.zone_id
  name       = "admin"
  type       = "A"
  records    = [var.public_ip]
}

resource "stackit_dns_record_set" "yatt_dns_record_set_auth" {
  depends_on = [stackit_dns_zone.yatt_dns_zone]
  project_id = var.project_id
  zone_id    = stackit_dns_zone.yatt_dns_zone.zone_id
  name       = "auth"
  type       = "A"
  records    = [var.public_ip]
}

resource "stackit_dns_record_set" "yatt_dns_record_set_api" {
  depends_on = [stackit_dns_zone.yatt_dns_zone]
  project_id = var.project_id
  zone_id    = stackit_dns_zone.yatt_dns_zone.zone_id
  name       = "api"
  type       = "A"
  records    = [var.public_ip]
}

resource "stackit_dns_record_set" "yatt_dns_record_set_rest_api" {
  depends_on = [stackit_dns_zone.yatt_dns_zone]
  project_id = var.project_id
  zone_id    = stackit_dns_zone.yatt_dns_zone.zone_id
  name       = "rest.api"
  type       = "A"
  records    = [var.public_ip]
}

resource "stackit_dns_record_set" "yatt_dns_record_set_graphql_api" {
  depends_on = [stackit_dns_zone.yatt_dns_zone]
  project_id = var.project_id
  zone_id    = stackit_dns_zone.yatt_dns_zone.zone_id
  name       = "graphql.api"
  type       = "A"
  records    = [var.public_ip]
}

resource "stackit_ske_cluster" "yatt_ske_cluster" {
  depends_on = [stackit_argus_instance.yatt_argus]
  project_id             = var.project_id
  name                   = "yatt"
  kubernetes_version_min = "1.29"

  node_pools = [
    {
      name         = "yatt"
      machine_type = "g1.2"
      minimum      = "1"
      maximum      = "2"
      availability_zones = ["eu01-3"]
    }
  ]

  maintenance = {
    enable_kubernetes_version_updates    = true
    enable_machine_image_version_updates = true
    start                                = "01:00:00Z"
    end                                  = "02:00:00Z"
  }

  extensions = {
    argus = {
      enabled           = true
      argus_instance_id = stackit_argus_instance.yatt_argus.instance_id
    }
  }
}
