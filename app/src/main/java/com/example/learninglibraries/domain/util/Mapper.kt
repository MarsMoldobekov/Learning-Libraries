package com.example.learninglibraries.domain.util

import org.modelmapper.ModelMapper
import org.modelmapper.config.Configuration
import org.modelmapper.convention.MatchingStrategies

object Mapper {
    class MapperDto : ModelMapper() {
        init {
            configuration.matchingStrategy = MatchingStrategies.LOOSE
            configuration.fieldAccessLevel = Configuration.AccessLevel.PRIVATE
            configuration.isFieldMatchingEnabled = true
            configuration.isSkipNullEnabled = false
        }
    }

    val mapper = MapperDto()

    inline fun <S, reified T> convert(source: S): T = mapper.map(source, T::class.java)
}