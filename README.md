# 🚀 TeamCity + Kubernetes + Helm CI/CD Demo

A production-style **CI/CD pipeline** using **TeamCity**, **Docker**, **Kubernetes**, and **Helm**.

This project demonstrates how to automatically build, push, and deploy an application to Kubernetes using a fully automated pipeline defined as code.

---

## 🧠 Overview

This project implements a complete CI/CD workflow:

```
GitHub → TeamCity → Docker → Kubernetes (Helm)
```

* Code push triggers pipeline
* Docker image is built & pushed
* Application is deployed to Kubernetes using Helm

> TeamCity is a CI/CD server used to automate build, test, and deployment workflows.

---

## 🛠️ Tech Stack

* CI/CD: TeamCity
* Containerization: Docker
* Orchestration: Kubernetes
* Deployment: Helm
* Language: Node.js
* Local Cluster: Docker Desktop Kubernetes

---

## 📂 Project Structure

```
.
├── app/                     # Node.js application
├── devops-demo-chart/      # Helm chart
├── .teamcity/              # Pipeline as code (Kotlin DSL)
└── README.md
```

---

## ⚙️ Prerequisites

Make sure you have:

* Docker installed
* Kubernetes cluster (Docker Desktop / Minikube / Cloud)
* kubectl installed
* Helm installed
* TeamCity server running

---

## 🔐 Required Secrets (TeamCity)

Go to:
**Project → Parameters**

Add:

| Name                  | Description               |
| --------------------- | ------------------------- |
| `env.DOCKER_USERNAME` | Docker Hub username       |
| `env.DOCKER_PASSWORD` | Docker Hub access token   |
| `env.KUBECONFIG_DATA` | Base64 encoded kubeconfig |

---

## 🔑 Generate Kubeconfig Secret

Run:

```bash
base64 ~/.kube/config | tr -d '\n'
```

Copy output → paste into:

```
env.KUBECONFIG_DATA
```

---

## 🚀 Setup Instructions

### 1️⃣ Clone Repo

```bash
git clone https://github.com/Awesome-SRE-Playground/teamcity-k8s-observability-demo.git
cd teamcity-k8s-observability-demo
```

---

### 2️⃣ Create Project in TeamCity

* Go to TeamCity UI
* Click **Create Project**
* Use GitHub repo URL
* Select:

```
Import settings from .teamcity/settings.kts
```

---

### 3️⃣ Configure Secrets

Add all required parameters in TeamCity UI.

---

### 4️⃣ Run Pipeline

Push code:

```bash
git commit -am "trigger pipeline"
git push
```

---

## ⚙️ Pipeline Flow

The pipeline performs:

1. Setup Kubernetes access (kubeconfig)
2. Install dependencies
3. Build Docker image
4. Login to Docker Hub
5. Push image
6. Deploy using Helm

---

## 📦 Helm Deployment

The app is deployed using:

```bash
helm upgrade --install devops-demo ./devops-demo-chart \
  -n demo-app \
  --create-namespace \
  --set image.tag=<build-number>
```

Helm ensures reproducible deployments by versioning Kubernetes manifests. ([hoop.dev][2])

---

## 🌐 Access Application

```bash
kubectl get svc -n demo-app
```

OR (Minikube):

```bash
minikube service devops-demo-service -n demo-app
```

---

## 🧠 Key Learnings

* CI/CD pipeline as code (Kotlin DSL)
* Secure secret management
* Docker image lifecycle
* Helm-based deployments
* Kubernetes debugging

---
