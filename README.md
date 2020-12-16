# **Commando's** om microservice te runnen
## Docker Compose
*docker-compose -f docker-compose.dev.yml up*
- om lokaal te starten

*docker-compose -f docker-compose.dev.yml down*
- om lokaal af te sluiten

*docker-compose -f docker-compose.dev.yml build repairs-service*
- nieuwe versie van repair-service bouwen

*docker-compose -f docker-compose.dev.yml restart repairs-service*
- updaten naar nieuwe versie

## In Kubernetes
*kubectl apply -k .ci/deploy/k8s/infrastructure*
- mongodb opzetten in cluster

*kubectl apply -k .ci/deploy/k8s/base*
- microservice opstarten of veranderingen toepassen

*kubectl port-forward svc/repairs-service 8052:80*
- port forwarding naar localhost met poort 8052
