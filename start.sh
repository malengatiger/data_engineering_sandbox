#! /bin/bash
echo " 🍐 🍐 🍐 🍐 🍐 CLOUD BUILD: Building the image for the SandBox5 app ...  🍐 🍐 🍐"

set -e
./mvnw compile com.google.cloud.tools:jib-maven-plugin:3.2.1:build -Dimage=gcr.io/gcp-sandbox-33/gcp-sandbox
set +e
echo " 🍎 🍎 🍎 🍎 🍎 Deploying the bloody Sandbox app!  🍎 🍎 🍎"
set -e
gcloud run deploy gcp-sandbox --image gcr.io/gcp-sandbox-33/gcp-sandbox --platform managed --allow-unauthenticated
set +e
echo " 🍎 🍎 🍎 🍎 🍎 Sandbox Deployment should be complete - check output!  🍎 🍎 🍎"
echo " 🥬 🥬 🥬 🥬 🥬 🥬 🥬 🥬 🥬 🥬 SandBox App is deployed on GCP 🥬 🥬 🥬"

echo "🔷 🔷 🔷 🔷 🔷 🔷 🔷 🔷 Service URL: https://gcp-sandbox-rjkpygdggq-ew.a.run.app"