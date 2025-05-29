package org.example.newteamultimateedition.users.repository


import org.example.newteamultimateedition.users.dao.UsersDao
import org.example.newteamultimateedition.users.dao.UsersEntity
import org.example.newteamultimateedition.users.mapper.UsersMapper
import org.example.newteamultimateedition.users.models.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*


@ExtendWith(MockitoExtension::class)
class UsersRepositoryImplTest {
 @Mock
 private lateinit var dao: UsersDao
 @Mock
 private lateinit var mapper: UsersMapper
 @InjectMocks
 private lateinit var repository: UsersRepositoryImpl

 private val usuario= User(
  "admin",
  "admin",
  true
 )
 private val usuario2=User(
  "carlos",
  "carlos",
  false
 )
 private val usuarioEntity=UsersEntity(
 "admin",
 "admin",
 true
 )
 private val usuarioEntity2=UsersEntity(
  "carlos",
  "carlos",
  false
 )

 @Test
 fun getAll() {
  whenever(dao.getAll()) doReturn listOf(usuarioEntity, usuarioEntity2)
  whenever(mapper.toModel(usuarioEntity)) doReturn usuario
  whenever(mapper.toModel(usuarioEntity2)) doReturn usuario2

  val result=repository.getAll()
  assertEquals(result.size,2,"debe contener dos usuarios")
  assertNotNull(result,"no deberia ser nulo")
  assertTrue(result.isNotEmpty())

  verify(dao,times(1)).getAll()
  verify(mapper, times(1)).toModel(usuarioEntity)
  verify(mapper, times(1)).toModel(usuarioEntity2)
 }

 @Test
 @DisplayName("get id  estando usuario")
 fun getById() {
  whenever(dao.getByName(usuario.name)) doReturn usuarioEntity
  whenever(mapper.toModel(usuarioEntity)) doReturn usuario

  val result=repository.getById(usuario.name)
  assertEquals(result,usuario,"deberia devolver el usuario")
  assertNotNull(result,"no deberia ser nulo")
  assertEquals(result!!.name,usuario.name,"deberia devolver el usuario")

  verify(dao,times(1)).getByName(usuario.name)
  verify(mapper, times(1)).toModel(usuarioEntity)
 }
 @Test
 @DisplayName("get id sin estar esa persona")
 fun getByIdBad() {
  whenever(dao.getByName("josefa")) doReturn null

  val result=repository.getById("josefa")

  assertNull(result,"deberia ser nulo")

  verify(dao,times(1)).getByName("josefa")
 }

 @Test
 @DisplayName("update estando bien")
 fun update() {
  whenever(dao.update(usuarioEntity,usuarioEntity.name)) doReturn 1
  whenever(mapper.toEntity(usuario)) doReturn usuarioEntity


  val result=repository.update(usuario,usuario.name)

  assertEquals(result!!.name,usuario.name,"deberia devolver el mismo usuario")
  assertEquals(result.password,usuario.password,"deberia devolver el mismo usuario")
  assertNotNull(result,"no deberia ser nulo")
  assertEquals(result.isAdmin,usuario.isAdmin,"deberia devolver el mismo usuario")

  verify(dao,times(1)).update(usuarioEntity,usuarioEntity.name)
  verify(mapper, times(1)).toEntity(usuario)
 }
 @Test
 @DisplayName("update no estando")
 fun updateNotPersona() {
  whenever(dao.update(usuarioEntity,usuarioEntity.name)) doReturn 0
  whenever(mapper.toEntity(usuario)) doReturn usuarioEntity

  val result=repository.update(usuario,usuario.name)

  assertNull(result,"no deberia ser nulo")


  verify(dao,times(1)).update(usuarioEntity,usuarioEntity.name)
  verify(mapper, times(1)).toEntity(usuario)
 }


 @Test
 @DisplayName("delete usuario")
 fun delete() {
  whenever(dao.getByName(usuarioEntity.name)) doReturn usuarioEntity
  whenever(dao.delete(usuarioEntity.name)) doReturn 1
  whenever(mapper.toModel(usuarioEntity)) doReturn usuario

  val result=repository.delete(usuarioEntity.name)

  assertEquals(result,usuario,"deberia devolver el usuario")
  assertNotNull(result,"no deberia ser nulo")
  assertEquals(result!!.name,usuario.name,"deberia devolver el mismo nombre")

  verify(dao,times(1)).delete(usuarioEntity.name)
  verify(mapper, times(1)).toModel(usuarioEntity)
  verify(dao, times(1)).getByName(usuarioEntity.name)
 }
 @Test
 @DisplayName("delete no estando")
 fun deleteNotPersona() {
  whenever(dao.getByName(usuarioEntity.name)) doReturn null



  val result=repository.delete(usuarioEntity.name)

  assertNull(result,"deberia ser nulo")


  verify(dao,times(1)).getByName(usuarioEntity.name)
  verify(mapper, times(0)).toModel(any())
  verify(dao, times(0)).delete(any())
 }
 @Test
 @DisplayName("delete estando y fallando")
 fun notDeletePersona() {
  whenever(dao.getByName(usuarioEntity.name)) doReturn usuarioEntity
  //whenever(mapper.toDatabaseModel(persona3)) doReturn persona2
  whenever(dao.delete(usuarioEntity.name)) doReturn 0


  val result=repository.delete(usuarioEntity.name)

  assertNull(result,"deberia ser nulo")


  verify(dao,times(1)).delete(usuarioEntity.name)
  verify(mapper, times(0)).toModel(usuarioEntity)
  verify(dao, times(1)).getByName(usuarioEntity.name)
 }

 @Test
 fun save() {
  whenever(mapper.toEntity(usuario)).thenReturn(usuarioEntity)
  whenever(dao.save(usuarioEntity)) doReturn 1

  val result=repository.save(usuario)
  assertEquals(result.name,"admin","deberia devolver el usuario")
  assertNotNull(result)
  assertEquals(result.password,usuario.password,"deberia devolver el usuario")

  verify(mapper, times(1)).toEntity(usuario)
  verify(dao, times(1)).save(usuarioEntity)
 }
}