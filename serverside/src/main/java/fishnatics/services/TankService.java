/*
 * @bot-written
 * 
 * WARNING AND NOTICE
 * Any access, download, storage, and/or use of this source code is subject to the terms and conditions of the
 * Full Software Licence as accepted by you before being granted access to this source code and other materials,
 * the terms of which can be accessed on the Codebots website at https://codebots.com/full-software-licence. Any
 * commercial use in contravention of the terms of the Full Software Licence may be pursued by Codebots through
 * licence termination and further legal action, and be required to indemnify Codebots for any loss or damage,
 * including interest and costs. You are deemed to have accepted the terms of the Full Software Licence on any
 * access, download, storage, and/or use of this source code.
 * 
 * BOT WARNING
 * This file is bot-written.
 * Any changes out side of "protected regions" will be lost next time the bot makes any changes.
 */
package fishnatics.services;

import org.springframework.data.querydsl.QSort;
import fishnatics.entities.*;
import fishnatics.repositories.TankRepository;
import fishnatics.repositories.auditing.AuditingRepository;
import fishnatics.graphql.utils.Where;
import fishnatics.graphql.utils.AuditQueryType;
import fishnatics.services.utils.QuerydslUtils;
import fishnatics.configs.security.auditing.CustomRevisionEntity;
import com.google.common.collect.Lists;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import com.google.common.collect.Sets;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import fishnatics.entities.enums.*;
import java.time.*;
import fishnatics.entities.FishEntity;
import fishnatics.repositories.FishRepository;

import lombok.NonNull;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.time.OffsetDateTime;

// % protected region % [Add any additional imports here] off begin
// % protected region % [Add any additional imports here] end

@Service
public class TankService extends AbstractService<TankEntity, TankRepository, TankEntityAudit> {

	private final String[] referenceNamesInTankEntity = {
		"fishTank",
	};

	// TODO change to service
	private final FishRepository fishRepository;

	private Validator validator;

	private AuditingRepository auditingRepository;

	// % protected region % [Add any additional class fields here] off begin
	// % protected region % [Add any additional class fields here] end

	@Autowired
	public TankService(
		// % protected region % [Add any additional constructor parameters here] off begin
		// % protected region % [Add any additional constructor parameters here] end
		FishRepository fishRepository,
		Validator validator,
		AuditingRepository auditingRepository,
		TankRepository repository
	) {
		super(
				// % protected region % [Add any additional superclass constructor arguments here] off begin
				// % protected region % [Add any additional superclass constructor arguments here] end
				repository
		);
		this.fishRepository = fishRepository;
		this.validator = validator;
		this.auditingRepository = auditingRepository;

		// % protected region % [Add any additional constructor logic here] off begin
		// % protected region % [Add any additional constructor logic here] end
	}

	/**
	 * Return the number of records available in the database.
	 *
	 * @return the number of records available in the database.
	 */
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@Transactional(readOnly = true)
	public long count() {
		// % protected region % [Add any additional logic for count before the main body here] off begin
		// % protected region % [Add any additional logic for count before the main body here] end

		long count = repository.count();

		// % protected region % [Add any additional logic for count before returning the count here] off begin
		// % protected region % [Add any additional logic for count before returning the count here] end

		return count;
	}

	/**
	 * Return the number of records available in the database with filters
	 *
	 * @return the number of records available in the database.
	 */
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@Transactional(readOnly = true)
	public long count(List<List<Where>> conditions) {
		// % protected region % [Add any additional logic for count with query before the main body here] off begin
		// % protected region % [Add any additional logic for count with query before the main body here] end

		long count = conditions != null ? repository.count(this.getQueryConditions(conditions)) : repository.count();

		// % protected region % [Add any additional logic for count with query before returning the count here] off begin
		// % protected region % [Add any additional logic for count with query before returning the count here] end

		return count;
	}

	/**
	 * Return an entity or a list of entities that have the given attribute Name.
	 *
	 * @param name the attribute against which the entities will be retrieved
	 * @return a list of entities that have the given attribute Name
	 */
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@Transactional(readOnly = true)
	public List<TankEntity> findByName(String name) {
		// % protected region % [Add any additional logic for findByName before the main body here] off begin
		// % protected region % [Add any additional logic for findByName before the main body here] end

		List<TankEntity> entities = Lists.newArrayList(repository.findByName(name));

		// % protected region % [Add any additional logic for findByName before returning the entities here] off begin
		// % protected region % [Add any additional logic for findByName before returning the entities here] end

		return entities;
	}

	/**
	 * Return an entity or a list of entities that have the given attribute Width.
	 *
	 * @param width the attribute against which the entities will be retrieved
	 * @return a list of entities that have the given attribute Width
	 */
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@Transactional(readOnly = true)
	public List<TankEntity> findByWidth(Double width) {
		// % protected region % [Add any additional logic for findByWidth before the main body here] off begin
		// % protected region % [Add any additional logic for findByWidth before the main body here] end

		List<TankEntity> entities = Lists.newArrayList(repository.findByWidth(width));

		// % protected region % [Add any additional logic for findByWidth before returning the entities here] off begin
		// % protected region % [Add any additional logic for findByWidth before returning the entities here] end

		return entities;
	}

	/**
	 * Return an entity or a list of entities that have the given attribute Length.
	 *
	 * @param length the attribute against which the entities will be retrieved
	 * @return a list of entities that have the given attribute Length
	 */
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@Transactional(readOnly = true)
	public List<TankEntity> findByLength(Double length) {
		// % protected region % [Add any additional logic for findByLength before the main body here] off begin
		// % protected region % [Add any additional logic for findByLength before the main body here] end

		List<TankEntity> entities = Lists.newArrayList(repository.findByLength(length));

		// % protected region % [Add any additional logic for findByLength before returning the entities here] off begin
		// % protected region % [Add any additional logic for findByLength before returning the entities here] end

		return entities;
	}

	/**
	 * Return an entity or a list of entities that have the given attribute Height.
	 *
	 * @param height the attribute against which the entities will be retrieved
	 * @return a list of entities that have the given attribute Height
	 */
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@Transactional(readOnly = true)
	public List<TankEntity> findByHeight(Double height) {
		// % protected region % [Add any additional logic for findByHeight before the main body here] off begin
		// % protected region % [Add any additional logic for findByHeight before the main body here] end

		List<TankEntity> entities = Lists.newArrayList(repository.findByHeight(height));

		// % protected region % [Add any additional logic for findByHeight before returning the entities here] off begin
		// % protected region % [Add any additional logic for findByHeight before returning the entities here] end

		return entities;
	}

	/**
	 * Return an entity or a list of entities that have the given attribute Last Cleaned.
	 *
	 * @param lastCleaned the attribute against which the entities will be retrieved
	 * @return a list of entities that have the given attribute Last Cleaned
	 */
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@Transactional(readOnly = true)
	public List<TankEntity> findByLastCleaned(OffsetDateTime lastCleaned) {
		// % protected region % [Add any additional logic for findByLastCleaned before the main body here] off begin
		// % protected region % [Add any additional logic for findByLastCleaned before the main body here] end

		List<TankEntity> entities = Lists.newArrayList(repository.findByLastCleaned(lastCleaned));

		// % protected region % [Add any additional logic for findByLastCleaned before returning the entities here] off begin
		// % protected region % [Add any additional logic for findByLastCleaned before returning the entities here] end

		return entities;
	}

	/**
	 * Return an entity or a list of entities that have the given attribute Clean.
	 *
	 * @param clean the attribute against which the entities will be retrieved
	 * @return a list of entities that have the given attribute Clean
	 */
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@Transactional(readOnly = true)
	public List<TankEntity> findByClean(CleanEnum clean) {
		// % protected region % [Add any additional logic for findByClean before the main body here] off begin
		// % protected region % [Add any additional logic for findByClean before the main body here] end

		List<TankEntity> entities = Lists.newArrayList(repository.findByClean(clean));

		// % protected region % [Add any additional logic for findByClean before returning the entities here] off begin
		// % protected region % [Add any additional logic for findByClean before returning the entities here] end

		return entities;
	}

	@Override
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@Transactional(readOnly = true)
	public Optional<TankEntity> findById(UUID id) {
		// % protected region % [Add any additional logic for findById before the main logic here] off begin
		// % protected region % [Add any additional logic for findById before the main logic here] end

		Optional<TankEntity> entity = repository.findById(id);

		// % protected region % [Add any additional logic for findById before returning the entity here] off begin
		// % protected region % [Add any additional logic for findById before returning the entity here] end

		return entity;
	}

	@Override
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@Transactional(readOnly = true)
	public List<TankEntity> findAllWithPage(int pageIndex, int pageSize) {
		// % protected region % [Add any additional logic for findAllWithPage before the main logic here] off begin
		// % protected region % [Add any additional logic for findAllWithPage before the main logic here] end

		List<TankEntity> entities = Lists.newArrayList(repository.findAll(PageRequest.of(pageIndex, pageSize)));

		// % protected region % [Add any additional logic for findAllWithPage before returning the entities here] off begin
		// % protected region % [Add any additional logic for findAllWithPage before returning the entities here] end

		return entities;
	}

	@Override
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@Transactional(readOnly = true)
	public List<TankEntity> findAllWithPageSorted(int pageIndex, int pageSize, Sort sortBy) {
		// % protected region % [Add any additional logic for findAllWithPageSorted before the main logic here] off begin
		// % protected region % [Add any additional logic for findAllWithPageSorted before the main logic here] end

		List<TankEntity> entities = Lists.newArrayList(repository.findAll(
			PageRequest.of(pageIndex, pageSize, getSort(sortBy)))
		);

		// % protected region % [Add any additional logic for findAllWithPageSorted before returning the entities here] off begin
		// % protected region % [Add any additional logic for findAllWithPageSorted before returning the entities here] end

		return entities;
	}

	@Override
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@Transactional(readOnly = true)
	public List<TankEntity> findSortedPageWithQuery(int pageIndex, int pageSize, List<List<Where>> conditions, Sort sortBy) {
		// % protected region % [Add any additional logic for findSortedPageWithQuery before the main logic here] off begin
		// % protected region % [Add any additional logic for findSortedPageWithQuery before the main logic here] end

		List<TankEntity> entities = Lists.newArrayList(repository.findAll(
			this.getQueryConditions(conditions), PageRequest.of(pageIndex, pageSize, getSort(sortBy)))
		);

		// % protected region % [Add any additional logic for findSortedPageWithQuery before returning the entities here] off begin
		// % protected region % [Add any additional logic for findSortedPageWithQuery before returning the entities here] end

		return entities;
	}

	@Override
	@Transactional
	@PreAuthorize("hasPermission('TankEntity', 'create')")
	public TankEntity create(TankEntity entity) {
		// % protected region % [Add any additional logic for create before the main logic here] off begin
		// % protected region % [Add any additional logic for create before the main logic here] end

		TankEntity entityToCreate = updateOldData(entity);

		// % protected region % [Add any additional logic for create before saving the entity here] off begin
		// % protected region % [Add any additional logic for create before saving the entity here] end

		TankEntity newEntity = this.save(entityToCreate);

		// % protected region % [Add any additional logic for create before returning the entity here] off begin
		// % protected region % [Add any additional logic for create before returning the entity here] end

		return newEntity;
	}

	@Override
	@Transactional
	@PreAuthorize("hasPermission('TankEntity', 'create')")
	public List<TankEntity> createAll(Iterable<TankEntity> entities) {
		// % protected region % [Add any additional logic for createAll before the main logic here] off begin
		// % protected region % [Add any additional logic for createAll before the main logic here] end

		Iterable<TankEntity> entitiesToCreate = StreamSupport.stream(entities.spliterator(), false).map(this::updateOldData).collect(Collectors.toList());

		// % protected region % [Add any additional logic for createAll before creating the entities here] off begin
		// % protected region % [Add any additional logic for createAll before creating the entities here] end

		List<TankEntity> newEntities = this.saveAll(entitiesToCreate);

		// % protected region % [Add any additional logic for createAll before returning the entities here] off begin
		// % protected region % [Add any additional logic for createAll before returning the entities here] end

		return newEntities;
	}

	@Override
	@Transactional
	@PreAuthorize("hasPermission('TankEntity', 'update')")
	public TankEntity update(TankEntity entity) {
		// % protected region % [Add any additional logic for update before the main logic here] off begin
		// % protected region % [Add any additional logic for update before the main logic here] end

		TankEntity entityToUpdate = updateOldData(entity);

		// % protected region % [Add any additional logic for update before updating the entity here] off begin
		// % protected region % [Add any additional logic for update before updating the entity here] end

		TankEntity newEntity = this.save(entityToUpdate);

		// % protected region % [Add any additional logic for update before returning the entity here] off begin
		// % protected region % [Add any additional logic for update before returning the entity here] end

		return newEntity;
	}

	@Override
	@Transactional
	@PreAuthorize("hasPermission('TankEntity', 'update')")
	public List<TankEntity> updateAll(Iterable<TankEntity> entities) {
		// % protected region % [Add any additional logic for updateAll before the main logic here] off begin
		// % protected region % [Add any additional logic for updateAll before the main logic here] end

		Iterable<TankEntity> entitiesToUpdate = StreamSupport.stream(entities.spliterator(), false).map(this::updateOldData).collect(Collectors.toList());

		// % protected region % [Add any additional logic for createAll before updating the entities here] off begin
		// % protected region % [Add any additional logic for createAll before updating the entities here] end

		List<TankEntity> newEntities = this.saveAll(entitiesToUpdate);

		// % protected region % [Add any additional logic for updateAll before returning the entities here] off begin
		// % protected region % [Add any additional logic for updateAll before returning the entities here] end

		return newEntities;
	}

	@Override
	@PreAuthorize("hasPermission('TankEntity', 'create') or hasPermission('TankEntity', 'update')")
	public TankEntity save(TankEntity entity) {
		// % protected region % [Add any additional logic for save before the main logic here] off begin
		// % protected region % [Add any additional logic for save before the main logic here] end

		validateEntity(entity);

		// % protected region % [Add any additional logic for save before saving the entity here] off begin
		// % protected region % [Add any additional logic for save before saving the entity here] end

		TankEntity newEntity = repository.save(entity);

		// % protected region % [Add any additional logic before returning the newly created entity here] off begin
		// % protected region % [Add any additional logic before returning the newly created entity here] end

		return newEntity;
	}

	@Override
	@PreAuthorize("hasPermission('TankEntity', 'create') or hasPermission('TankEntity', 'update')")
	public List<TankEntity> saveAll(Iterable<TankEntity> entities) {
		// % protected region % [Add any additional logic for saveAll before the main logic here] off begin
		// % protected region % [Add any additional logic for saveAll before the main logic here] end

		List<TankEntity> newEntities = Lists.newArrayList(repository.saveAll(entities));

		// % protected region % [Add any additional logic for saveAll before returning the entities here] off begin
		// % protected region % [Add any additional logic for saveAll before returning the entities here] end

		return newEntities;
	}

	@Override
	@PreAuthorize("hasPermission('TankEntity', 'delete')")
	public void deleteById(UUID id) {
		// % protected region % [Add any additional logic for deleteById before the main logic here] off begin
		// % protected region % [Add any additional logic for deleteById before the main logic here] end

		TankEntity entity = repository.findById(id).orElseThrow();

		repository.deleteById(id);
	}

	@Override
	@PreAuthorize("hasPermission('TankEntity', 'delete')")
	public void deleteAllByIds(Iterable<UUID> ids) {
		// % protected region % [Add any additional logic for deleteAll before the main logic here] off begin
		// % protected region % [Add any additional logic for deleteAll before the main logic here] end

		// Do this since Spring repository requires full entities to delete them.
		ids.forEach(this::deleteById);
	}

	@Override
	// TODO change to the audit operation into a separate operation
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<TankEntityAudit> getAudits() {
		// % protected region % [Add any additional logic for getAudits before the main logic here] off begin
		// % protected region % [Add any additional logic for getAudits before the main logic here] end

		List<Object[]> rawAudits = auditingRepository.getAudits(TankEntity.class);

		// % protected region % [Add any additional processing before converting to a list of TankEntityAudit here] off begin
		// % protected region % [Add any additional processing before converting to a list of TankEntityAudit here] end

		return mapRawAudits(rawAudits);
	}

	@Override
	@PreAuthorize("hasPermission('TankEntity', 'read')")
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<TankEntityAudit> getAuditsByEntityId(UUID id) {

		// % protected region % [Add any additional processing before converting to a list of TankEntityAudit as filtered by id here] off begin
		// % protected region % [Add any additional processing before converting to a list of TankEntityAudit as filtered by id here] end

		List<Object[]> rawAudits = auditingRepository.getAuditsByEntityId(TankEntity.class, id);

		// % protected region % [Add any additional logic before returning the list of entity audits filtered by id here] off begin
		// % protected region % [Add any additional logic before returning the list of entity audits filtered by id here] end

		return mapRawAudits(rawAudits);
	}


	/**
	 * Map the list of raw audits to a list of TankEntityAudit entities
	 *
	 * @param rawAudits The list of raw audits to map
	 * @returns The mapped list of raw audits as a list of TankEntityAudits
	 */
	private List<TankEntityAudit> mapRawAudits(List<Object[]> rawAudits) {
		List<TankEntityAudit> audits = rawAudits.stream().map(this::mapRawAudit)
				.sorted(Collections.reverseOrder(Comparator.comparing(AbstractEntityAudit::getTimestamp)))
				.collect(Collectors.toList());

		// % protected region % [Add any additional logic before returning the list of mapped entity audits here] off begin
		// % protected region % [Add any additional logic before returning the list of mapped entity audits here] end

		return audits;
	}

	/**
	 * Update the data in audit
	 * Change the ProxySet to Hashset to avoid lazy load issue in graphql
	 * @param tank tank to be updated
	 * @return Entity in audit with HashSet as relationship
	 */
	private TankEntity updateAuditData(TankEntity tank) {


		// % protected region % [Customise your update audit data here] off begin
		// % protected region % [Customise your update audit data here] end

		return tank;
	}

	/**
	 * Map a raw audit object to the RegisteredServiceProviderEntityAudit object.
	 *
	 * Handles system changes in the audit log.
	 *
	 * @param rawAudit The raw audit data
	 * @return mapped audit object
	 */
	private TankEntityAudit mapRawAudit(Object[] rawAudit) {
		TankEntity entityAtRevision = updateAuditData((TankEntity) rawAudit[0]);
		CustomRevisionEntity revision = (CustomRevisionEntity) rawAudit[1];
		AuditQueryType auditType = AuditQueryType.getType((RevisionType) rawAudit[2]);

		// Anything performed internally may not have a user associated with it,
		// therefore they can be called system.
		boolean isSystem = (revision.getAuthor() == null);

		return new TankEntityAudit(
				entityAtRevision,
				revision.getModifiedAt(),
				auditType,

				// We don't care about what the UUID is if it is the system.
				(isSystem) ? UUID.randomUUID() : revision.getAuthor().getId(),
				(isSystem) ? "System" : revision.getAuthor().getFirstName(),
				(isSystem) ? "" : revision.getAuthor().getLastName()
		);
	}

	/**
	 * Create the predicate for the querydsl based on one condition
	 *
	 * @param condition Single condition used in the query
	 * @return querydsl predicate
	 */
	protected Predicate processCondition(Where condition) {
		// % protected region % [Add any additional logic for processCondition before the main logic here] off begin
		// % protected region % [Add any additional logic for processCondition before the main logic here] end

		Predicate predicate = null;

		QTankEntity entity = QTankEntity.tankEntity;

		switch (condition.getPath()) {
			case "created":
				// % protected region % [Add any additional logic before the query parameters of created here] off begin
				// % protected region % [Add any additional logic before the query parameters of created here] end

				predicate = QuerydslUtils.getDefaultPredicate(entity.created, condition.getOperation(), condition.getValue());

				// % protected region % [Add any additional logic after the query parameters of created here] off begin
				// % protected region % [Add any additional logic after the query parameters of created here] end

				break;
			case "modified":
				// % protected region % [Add any additional logic before the query parameters of modified here] off begin
				// % protected region % [Add any additional logic before the query parameters of modified here] end

				predicate = QuerydslUtils.getDefaultPredicate(entity.modified, condition.getOperation(), condition.getValue());

				// % protected region % [Add any additional logic after the query parameters of modified here] off begin
				// % protected region % [Add any additional logic after the query parameters of modified here] end

				break;
			case "name":
				// % protected region % [Add any additional logic before the query parameters of entity properties here] off begin
				// % protected region % [Add any additional logic before the query parameters of entity properties here] end

				predicate = QuerydslUtils.getDefaultPredicate(entity.name, condition.getOperation(), condition.getValue());

				// % protected region % [Add any additional logic after the query parameters of entity properties here] off begin
				// % protected region % [Add any additional logic after the query parameters of entity properties here] end

				break;
			// % protected region % [Add any additional cases for the custom query parameters here] off begin
			// % protected region % [Add any additional cases for the custom query parameters here] end
		}

		// % protected region % [Add any additional logic for processCondition after the main logic here] off begin
		// % protected region % [Add any additional logic for processCondition after the main logic here] end

		return predicate;
	}

	/**
	 * Create the predicate for the querydsl based on one single List of Where
	 * The List are connect with 'and' statement
	 *
	 * @param conditions Single list of where statement. The conditions are connected with "or"
	 * @return querydsl predicate
	 */
	protected Predicate processConditionList(List<Where> conditions) {
		List<Predicate> predicates = conditions.stream()
				.map(this::processCondition)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		return ExpressionUtils.anyOf(predicates);
	}

	/**
	 * Transfer a list of conditions to one querydsl predicate
	 *
	 * @param conditions A list of conditions
	 * @return a single query dsl predicate
	 */
	@Override
	protected Predicate getQueryConditions(List<List<Where>> conditions) {
		// % protected region % [Add any additional logic for getQueryConditions before the main logic here] off begin
		// % protected region % [Add any additional logic for getQueryConditions before the main logic here] end

		List<Predicate> predicates = conditions.stream()
				.map(this::processConditionList)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		// % protected region % [Add any additional logic for getQueryConditions after the main logic here] off begin
		// % protected region % [Add any additional logic for getQueryConditions after the main logic here] end
		return ExpressionUtils.allOf(predicates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected TankEntity updateOldData(TankEntity entity) {
		// % protected region % [Add any additional logic for update references before the main logic here] off begin
		// % protected region % [Add any additional logic for update references before the main logic here] end
		TankEntity entityToUpdate;
		// Check if this is an update operation or a create operation.
		if (entity.getId() != null) {
			// Copy the updated entity data to the entity find from database
			entityToUpdate = repository.findById(entity.getId()).orElseThrow();
			BeanUtils.copyProperties(entity, entityToUpdate, this.referenceNamesInTankEntity);

		} else {
			entityToUpdate = entity;

			// % protected region % [Add any additional logic before processing the new entity's data here] off begin
			// % protected region % [Add any additional logic before processing the new entity's data here] end

			// % protected region % [Add any additional logic after processing the new entity's data here] off begin
			// % protected region % [Add any additional logic after processing the new entity's data here] end
		}

		// Outgoing one to many reference
		if (!entity.getFishTankIds().isEmpty()) {
			Iterable<FishEntity> fishTankEntities =
				this.fishRepository.findAllById(entity.getFishTankIds());
			// TODO add the invalid field exception
			entityToUpdate.setFishTank(Sets.newHashSet(fishTankEntities));
		} else {
			entityToUpdate.setFishTank(new HashSet<>());
		}


		// % protected region % [Add any additional logic for update references after the main logic here] off begin
		// % protected region % [Add any additional logic for update references after the main logic here] end

		return entityToUpdate;
	}

	/**
	 * Validate the entity
	 * @param entity The entity to validate
	 * @throws ConstraintViolationException Throw ConstraintViolationException when entity is invalid
	 */
	protected void validateEntity(TankEntity entity) {
		// % protected region % [Add any additional logic for validate entity before the main logic here] off begin
		// % protected region % [Add any additional logic for validate entity before the main logic here] end

		Set<ConstraintViolation<TankEntity>> errors =  this.validator.validate(entity);
		if (!errors.isEmpty()) {
			throw new ConstraintViolationException(errors);
		}

		// % protected region % [Add any additional logic for validate entity after the main logic here] off begin
		// % protected region % [Add any additional logic for validate entity after the main logic here] end
	}


	/**
	 * Get the sort as given by the sort by for the TankEntity.
	 * This allows fore more advanced sorting algorithms and methods to be compiled and returned.
	 *
	 * In the case that a custom sort is provided, only a single sort is returned.
	 *
	 * @param sortBy Submitted sort by
	 * @return sortBy if simple, for custom sort return a QSort.by(QSort Method).
	 */
	private Sort getSort (Sort sortBy) {
		Iterator it = sortBy.iterator();

		while (it.hasNext()) {
			Sort.Order currentOrder = (Sort.Order)it.next();

			// % protected region % [Customise your sort method here by returning early] off begin
			// % protected region % [Customise your sort method here by returning early] end
		}
		return sortBy;
	}



	// % protected region % [Add any additional class methods here] off begin
	// % protected region % [Add any additional class methods here] end
}
