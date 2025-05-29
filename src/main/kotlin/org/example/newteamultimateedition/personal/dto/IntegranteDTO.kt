package org.example.newteamultimateedition.personal.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
/**
 * Clase Serializable de Integrante
 * @property id Identificador del objeto
 * @property nombre Nombre del integrante
 * @property apellidos Apellidos del integrante
 * @property fechaNacimiento Fecha de nacimiento del integrante
 * @property fechaIncorporacion Fecha de incorporacion al equipo del integrante
 * @property pais Pais de origen del integrante
 * @property especialidad Especializacion del integrante
 * @property salario Salario del integrante
 * @property posicion Posicion de la que juega el integrante
 * @property dorsal Dorsal del integrante
 * @property altura Altura
 * @property peso Peso del integrante
 * @property goles Numero de goles que ha marcado el integrante
 * @property partidosJugados Numero de partidos jugados por el integrante
 * @property minutosJugados Numero de minutos jugados por el integrante
 * @property imagen imagen de perfil asignada al integrante
 */
@Serializable
@SerialName("personal")
data class IntegranteDTO(
    @SerialName("id")
    val id: Long,
    @SerialName("nombre")
    @XmlElement
    val nombre: String,
    @SerialName("apellidos")
    @XmlElement
    val apellidos: String,
    @SerialName("fecha_nacimiento")
    @XmlElement
    val fechaNacimiento: String,
    @SerialName("fecha_incorporacion")
    @XmlElement
    val fechaIncorporacion: String,
    @SerialName("salario")
    @XmlElement
    val salario: Double,
    @SerialName("pais")
    @XmlElement
    val pais: String,
    @SerialName("rol")
    @XmlElement
    val rol: String,
    @SerialName("especialidad")
    @XmlElement
    val especialidad: String?,
    @SerialName("posicion")
    @XmlElement
    val posicion: String?,
    @SerialName("dorsal")
    @XmlElement
    val dorsal: Int?,
    @SerialName("altura")
    @XmlElement
    val altura: Double?,
    @SerialName("peso")
    @XmlElement
    val peso: Double?,
    @SerialName("goles")
    @XmlElement
    val goles: Int?,
    @SerialName("partidos_jugados")
    @XmlElement
    val partidosJugados: Int?,
    @SerialName("minutos_jugados")
    @XmlElement
    val minutosJugados: Int?,
    @SerialName("imagen")
    @XmlElement
    val imagen: String,
    @SerialName("isDeleted")
    @XmlElement
    val isDeleted: Boolean=false,
): java.io.Serializable