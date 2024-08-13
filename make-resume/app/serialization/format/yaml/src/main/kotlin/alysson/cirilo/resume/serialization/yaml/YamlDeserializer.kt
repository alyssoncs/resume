package alysson.cirilo.resume.serialization.yaml

import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.serialization.models.SerializableResume
import alysson.cirilo.resume.serialization.toDomain
import com.charleskorn.kaml.Yaml

fun deserializeYaml(yamlResume: String): Resume {
    val serializableResume = Yaml.default.decodeFromString(SerializableResume.serializer(), yamlResume)
    return serializableResume.toDomain()
}
