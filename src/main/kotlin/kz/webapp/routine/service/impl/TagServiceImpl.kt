package kz.webapp.routine.service.impl


import kz.webapp.routine.exception.TagException
import kz.webapp.routine.exception.TagNotExistsException
import kz.webapp.routine.model.dto.AddTagDto
import kz.webapp.routine.model.entity.TagEntity
import kz.webapp.routine.repository.TagRepo
import kz.webapp.routine.service.TagService
import kz.webapp.routine.service.Utils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class TagServiceImpl(
    val tagRepo: TagRepo,
    val utils: Utils
): TagService {
    val logger: Logger = LoggerFactory.getLogger(TagService::class.java)

    override fun addTag(tagDto: AddTagDto) {
        val tagEntity = TagEntity (0,tagDto.tag)
        if (tagDto.tag !in utils.getAllTags()) {
            //save entity with separate function of try-catch block
            entitySaveTryCatchBlock(
                tagEntity,
                "Successfully created new tag with ID ${tagEntity.tagId}",
                "Failed to create tag with id ${tagEntity.tagId}"
            )
        } else {
            logger.error("The tag with name ${tagDto.tag} is already in the list")
        }
    }

    override fun delTag(id: Long) {
        val tag = tagRepo.findByIdOrNull(id)

        if (tag != null) {
            tagRepo.deleteById(id)
            logger.info("The tag with ID $id was successfully deleted")
        } else {
            val msg = "No tag with id $id found"
            logger.error(msg)
            throw TagNotExistsException(msg)
        }
    }

    override fun showTags(): List<TagEntity> {
        return tagRepo.findAll()
    }

    //-------------------------------------------------------------
    //------------------ private functions block ------------------
    //-------------------------------------------------------------

    private fun entitySaveTryCatchBlock(entity: TagEntity, msg: String, errMsg: String) {
        try {
            tagRepo.save(entity)
            logger.info(msg)
        } catch(e: TagException) {
            logger.error(errMsg)
            logger.error(e.message)
            throw (TagException(errMsg))
        }
    }

}