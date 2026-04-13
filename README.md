# 🚀 TeamCity + Kubernetes + Helm CI/CD Demo

A production-style **CI/CD pipeline** using **TeamCity, Docker, Kubernetes, and Helm**.

This project demonstrates how to automatically build, push, and deploy an application to Kubernetes using a fully automated pipeline defined as code.

---

## 🧠 Overview

```text
GitHub → TeamCity → Docker → Kubernetes (Helm)
```

* Code push triggers pipeline
* Docker image is built & pushed
* Application is deployed to Kubernetes

---

## 🛠️ Tech Stack

* CI/CD: TeamCity
* Containerization: Docker
* Orchestration: Kubernetes
* Deployment: Helm + Kubernetes manifests
* Language: Node.js
* Local Cluster: Docker Desktop Kubernetes

---

## 📂 Project Structure

```text
.
├── app/                     # Node.js application
├── devops-demo-chart/       # Helm chart (preferred deployment)
├── k8s/                     # Raw Kubernetes manifests (alternative)
├── .teamcity/               # Pipeline as code (Kotlin DSL)
└── README.md
```

---

## 📦 Deployment Approaches

This project supports **2 deployment methods**:

### 🔹 1. Helm (Used in CI/CD ✅)

* Dynamic deployments
* Version-controlled releases
* Used in TeamCity pipeline

### 🔹 2. Raw Kubernetes Manifests

* Simple YAML-based deployment
* Useful for learning/debugging

---

## ⚙️ Prerequisites

* Docker installed
* Kubernetes cluster (Docker Desktop / Minikube / Cloud)
* kubectl installed
* Helm installed
* TeamCity server running

---

## 🔐 Required Secrets (TeamCity)

Go to:
**Project → Parameters**

| Name                  | Description               |
| --------------------- | ------------------------- |
| `env.DOCKER_USERNAME` | Docker Hub username       |
| `env.DOCKER_PASSWORD` | Docker Hub access token   |
| `env.KUBECONFIG_DATA` | Base64 encoded kubeconfig |

---

## 🔑 Generate Kubeconfig

```bash
base64 ~/.kube/config | tr -d '\n'
```

---

## 🚀 Setup Instructions

### 1️⃣ Clone Repository

```bash
git clone https://github.com/Awesome-SRE-Playground/teamcity-k8s-observability-demo.git
cd teamcity-k8s-observability-demo
```

---

### 2️⃣ Create Project in TeamCity

* Go to TeamCity UI
* Click **Create Project from URL**
* Enter repository URL
* Select:

```text
Import settings from .teamcity/settings.kts
```

---

### 3️⃣ Add Secrets

Add required parameters in TeamCity.

---

### 4️⃣ Trigger Pipeline

```bash
git commit -am "trigger pipeline"
git push
```

---

## ⚙️ CI/CD Pipeline Flow

1. Setup Kubernetes access (kubeconfig)
2. Install dependencies
3. Build Docker image
4. Login to Docker Hub
5. Push image
6. Deploy using Helm

---

## 📦 Helm Deployment

```bash
helm upgrade --install devops-demo ./devops-demo-chart \
  -n demo-app \
  --create-namespace \
  --set image.tag=<build-number>
```

---

## 📄 Kubernetes Deployment (Alternative)

Apply manually:

```bash
kubectl apply -f k8s/
```

---

## 🌐 Access Application

```bash
kubectl get svc -n demo-app
```

OR:

```bash
minikube service devops-demo-service -n demo-app
```

---


## 🧠 Key Learnings

* CI/CD pipeline as code
* Kubernetes deployments
* Helm vs raw manifests
* Secret management in CI/CD
* Debugging real-world issues

---

## 🎯 Demo Flow

1. Show running app
2. Make code change
3. Push to GitHub
4. Pipeline runs
5. App auto-updates

---
