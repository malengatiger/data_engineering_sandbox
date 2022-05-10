#! /bin/bash
echo " 🍐 🍐 🍐 🍐 🍐 CLOUD BUILD: Building the image for the SandBox5 app ...  🍐 🍐 🍐"
gcloud builds submit --tag gcr.io/gcp-sandbox-33/gcp-sandbox
echo " 🍎 🍎 🍎 🍎 🍎 Deploying the bloody app!  🍎 🍎 🍎"
gcloud run deploy gcp-sandbox --image gcr.io/gcp-sandbox-33/gcp-sandbox --platform managed
echo " 🍎 🍎 🍎 🍎 🍎 Deploy should be complete - check output!  🍎 🍎 🍎"
echo " 🥬 🥬 🥬 🥬 🥬 🥬 🥬 🥬 🥬 🥬 SandBox App is deployed on GCP 🥬 🥬 🥬"