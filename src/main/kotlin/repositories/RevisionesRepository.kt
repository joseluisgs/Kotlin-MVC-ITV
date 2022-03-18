package repositories

import models.Revision

class RevisionesRepository : CRUDRepository<Revision, Int> {
    private var revisones = mutableMapOf<Int, Revision>()

    override fun findAll(): List<Revision> {
        return revisones.values.toList()
    }

    override fun findById(id: Int): Revision? {
        return revisones[id]
    }

    override fun save(entity: Revision): Revision? {
        revisones[entity.id] = entity
        return entity
    }

    override fun update(id: Int, entity: Revision): Revision? {
        revisones[id] = entity
        return entity
    }

    override fun delete(id: Int): Revision? {
        return revisones.remove(id)
    }

    override fun deleteAll() {
        revisones.clear()
    }
}