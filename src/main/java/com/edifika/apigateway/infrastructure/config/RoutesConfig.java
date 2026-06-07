package com.edifika.apigateway.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Gateway routes configuration.
 * Each microservice route is defined here using environment variables
 * to support local and production deployments.
 */
@Configuration
public class RoutesConfig {

    @Value("${services.iam.url}")
    private String iamServiceUrl;

    @Value("${services.residential.url}")
    private String residentialServiceUrl;

    @Value("${services.reservation.url}")
    private String reservationServiceUrl;

   /*

    @Value("${services.payment.url}")
    private String paymentServiceUrl;

    @Value("${services.communication.url}")
    private String communicationServiceUrl;

    @Value("${services.report.url}")
    private String reportServiceUrl;

    @Value("${services.notification.url}")
    private String notificationServiceUrl;

    */

    @Bean
    public RouteLocator edifikaRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                // IAM Service
                .route("iam-authentication", r -> r
                        .path("/api/v1/authentication/**")
                        .uri(iamServiceUrl))
                .route("iam-roles", r -> r
                        .path("/api/v1/roles/**")
                        .uri(iamServiceUrl))
                .route("iam-users", r -> r
                        .path("/api/v1/users/**")
                        .uri(iamServiceUrl))

                // Residential Management Service
                .route("residential-all", r -> r
                        .path("/api/v1/residential/**")
                        .uri(residentialServiceUrl))

                // Reservation Service
                .route("reservation-reservations", r -> r
                        .path("/api/v1/reservations/**")
                        .uri(reservationServiceUrl))


                /* Communication Service
                .route("communication-announcements", r -> r
                        .path("/api/v1/announcements/**")
                        .uri(communicationServiceUrl))
                .route("communication-surveys", r -> r
                        .path("/api/v1/surveys/**")
                        .uri(communicationServiceUrl))

                // Report Service
                .route("report-service", r -> r
                        .path("/api/v1/reports/**")
                        .uri(reportServiceUrl))

                // Notification Service
                 .route("notification-service", r -> r
                        .path("/api/v1/notifications/**")
                        .uri(notificationServiceUrl))

                // Payment Service
                .route("payment-debts", r -> r
                        .path("/api/v1/debts/**")
                        .uri(paymentServiceUrl))
                .route("payment-payments", r -> r
                        .path("/api/v1/payments/**")
                        .uri(paymentServiceUrl))

                        */
                .build();
    }
}