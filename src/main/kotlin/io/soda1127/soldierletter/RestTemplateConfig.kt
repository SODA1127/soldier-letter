package io.soda1127.soldierletter

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class RestTemplateConfig {
    @Bean
    fun restTemplate(builder: RestTemplateBuilder) = builder.build()
}