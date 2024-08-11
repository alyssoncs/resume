package alysson.cirilo.resume.serialization.json

import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.serialization.models.SerializableResume
import alysson.cirilo.resume.serialization.toDomain
import kotlinx.serialization.json.Json

fun deserializeJson(jsonResume: String): Resume {
    val serializableResume = Json.decodeFromString<SerializableResume>(jsonResume)
    return serializableResume.toDomain()
}
