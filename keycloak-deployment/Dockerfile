# keycloak-deployment/Dockerfile
FROM quay.io/keycloak/keycloak:24.0.3

WORKDIR /opt/keycloak

ENV KC_HEALTH_ENABLED=true \
    KC_METRICS_ENABLED=true \
    KEYCLOAK_ADMIN=admin \
    KEYCLOAK_ADMIN_PASSWORD=admin123

RUN /opt/keycloak/bin/kc.sh build

EXPOSE 8080

CMD [ "start-dev" ]
