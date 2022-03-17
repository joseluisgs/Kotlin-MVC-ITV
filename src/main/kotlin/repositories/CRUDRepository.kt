package repositories

interface CRUDRepository<T, ID> {
    fun findAll(): List<T>
    fun findById(id: ID): T?
    fun save(entity: T): T?
    fun update(id: ID, entity: T): T?
    fun delete(id: ID): T?
    fun deleteAll()
}