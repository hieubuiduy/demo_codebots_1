import {Component, ComponentRef, Input, OnInit} from '@angular/core';
import {IModalDialog, IModalDialogOptions} from "ngx-modal-dialog";
import {SpeciesModel} from "../../../../../../models/species/species.model";
import {
	Expand,
	OrderBy,
	PassableStateConfig,
	QueryOperation,
	QueryParams,
	Where
} from "../../../../../../lib/services/http/interfaces";
import {Observable, Subject} from "rxjs";
import {ModelProperty, ModelRelation} from "../../../../../../lib/models/abstract.model";
import {Action, HeaderOption, LoadingType} from "../../../../../../lib/components/collection/collection.component";
import {
	FilterQuestion,
	FilterQuestionType
} from "../../../../../../lib/components/collection/collection-filter.component";
import {IconPosition} from "../../../../../../lib/components/button/button.component";
import {RouterState} from "../../../../../../models/model.state";
import {Store} from "@ngrx/store";
import {SpeciesModelState} from "../../../../../../models/species/species.model.state";
import {FormGroup} from "@angular/forms";
import * as modelAction from '../../../../../../models/species/species.model.action';
import {
	getCountSpeciesModels,
	getSpeciesCollectionModels
} from "../../../../../../models/species/species.model.selector";
import {DatepickerComponent, DatepickerConfig} from "../../../../../../lib/components/datepicker/datepicker.component";
import {DropdownConfig} from "../../../../../../lib/components/dropdown/dropdown.component";

@Component({
  selector: 'app-species-reference-filter',
  templateUrl: './species-reference-filter.component.html',
  styleUrls: ['./species-reference-filter.component.scss']
})
export class SpeciesReferenceFilterComponent implements OnInit, IModalDialog {

	/**
	 * The name of the fields which are used in the search box
	 */
	private searchFields: string[] = [
		...SpeciesModel.searchFields,
	];

	/**
	 * Where statements used in search
	 * Connected with 'or' operation
	 */
	private searchConditions: Where[] = [];

	/**
	 * Where statements used in filtering
	 * Connected with 'and' operation
	 */
	private filterConditions: Where[][] = [];

	/**
	 * List of Medical Staffs that will be managed by this CRUD tile. Note that this list is not complete, i.e. it
	 * does not represent all models in the database. Instead it represents a slice or page of the complete list.
	 */
	speciesObs: Observable<SpeciesModel[]>;

	/**
	 * The total number of entities in the database.
	 */
	speciesCount: number = 0;

	/**
	 * List of all property names for this entity.
	 */
	modelProperties: ModelProperty[] = SpeciesModel.getProps();

	/**
	 * List of all relations of the model
	 */
	modelRelations: { [name: string]: ModelRelation } = SpeciesModel.getRelations();

	/**
	 * The collection id used in the store
	 * Default to be the uuid of the tile, you could change this to custom id you want to share in different component
	 * But this must to be unique to avoid mess up the data
	 */
	@Input()
	collectionId: string = 'species-reference-filter';

	/**
	 * Which type of pagination to be used in the collection.
	 */
	@Input()
	loadingType: LoadingType | string = LoadingType.PAGINATION;

	/**
	 * A page index indicates the start index of a "page", which default to 0.
	 */
	@Input()
	pageIndex: number = 0;

	/**
	 * How many items are included in this page.
	 */
	@Input()
	pageSize: number = 10;

	/**
	 * Default ordering of the items.
	 */
	@Input()
	orderBy: OrderBy[] = [
		{
			path: 'created',
			descending: true
		}
	];

	/**
	 * Default conditions to be applied when the component first loaded.
	 */
	private readonly defaultWheres: Where[][] = [];

	/**
	 * Default references to expand
	 * In CRUD tile, default to expand all the references
	 */
	private get defaultExpands(): Expand[] {
		// TODO fix the hard code s once the standard is confirmed
		let expands: Expand[] = Object.entries(SpeciesModel.getRelations()).map(
			([key, entry]): Expand => {
				return {
					name: key,
					fields: ['id', entry.displayName]
				};
			}
		);
		return expands;
	}

	/**
	 * Where can be passed in as where statement in query
	 */
	@Input()
	where: Where[][] = [];

	/**
	 * The reference want to fetch from the server side
	 */
	@Input()
	expands: Expand[] = [];

	/**
	 * The query parameters for the collection
	 */
	get queryParams(): QueryParams {
		return {
			pageIndex: this.pageIndex,
			pageSize: this.pageSize,
			orderBy: this.orderBy,
			where: [
				...this.where,
				...this.defaultWheres,
				...this.filterConditions,
				this.searchConditions,
			],
			expands: [
				...this.expands,
				...this.defaultExpands
			],
		};
	}

	@Input()
	set queryParams(queryParams: QueryParams) {
		this.pageIndex = queryParams.pageIndex;
		this.pageSize = queryParams.pageSize;
		this.orderBy = queryParams.orderBy;
		this.where = queryParams.where;
		this.expands = queryParams.expands;
	}

	/**
	 * Whether the current page is the last page or not
	 */
	get isLastPage(): boolean {
		return Math.max(Math.floor((this.speciesCount - 1) / this.pageSize), 0) === this.pageIndex;
	}

	/**
	 * List of all header options for the collection
	 */
	readonly headerOptions: HeaderOption[] = this.modelProperties.map(prop => {
		return {
			...prop,
			sortable: true,
			sourceDirectFromModel: true,
			valueSource: prop.name
		} as HeaderOption;
	}).filter(opt => opt.name !== 'id').filter(opt => !opt.doHide);

	/**
	 * Additional actions to be added as buttons on the menu of the collection.
	 */
	collectionActions: Action[] = [];

	/**
	 * Additional actions to be added as buttons on each row of the collection.
	 */
	itemActions: Action[] = [
		{
			label: 'Select',
			icon: 'look',
			iconPos: IconPosition.TOP,
			showIcon: true,
			isAdditional: false
		},
	];

	/**
	 * The questions to be used in the filter
	 * TODO fix the enum value in java
	 */
	filterQuestions: FilterQuestion[] = [];

	/**
	 * Subject to close modal
	 */
	closeSubject: Subject<void>;

	/**
	 * data to modify
	 */
	valueChangeSubject: Subject<any>;


	constructor(
		private readonly store: Store<{ model: SpeciesModelState }>,
		private readonly routerStore: Store<{ router: RouterState }>,
	) {
		this.speciesObs = this.store.select(getSpeciesCollectionModels, this.collectionId);

		this.store.select(getCountSpeciesModels).subscribe(
			(count) => this.speciesCount = count
		);
	}

  ngOnInit() {
	  this.store.dispatch(new modelAction.InitialiseSpeciesCollectionState({
		  queryParams: this.queryParams,
		  collectionId: this.collectionId
	  }));

	  this.store.dispatch(new modelAction.FetchSpeciesModelsWithQuery({
			  queryParams: this.queryParams,
			  collectionId: this.collectionId
		  },
		  [
			  new modelAction.CountSpeciesModels()
		  ]
	  ));
  }

	dialogInit(reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
		this.closeSubject = options.closeDialogSubject;
		this.valueChangeSubject = options.data.valueChangeSubject;
	}

	/**
	 * Triggered whenever a custom action against an item is clicked.
	 */
	onItemActionClicked(event: { actionName: string, payload?: any }) {
		const model: SpeciesModel = event.payload.model;
		this.valueChangeSubject.next({
			value: model.id,
			valueToDisplay: model.name
		});
		this.closeSubject.next();
	}

	/**
	 * Triggered when a header in the collection is clicked
	 */
	onCollectionSort($event: OrderBy[]) {

		this.orderBy = $event;


		let stateConfig: PassableStateConfig<SpeciesModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};


		this.store.dispatch(new modelAction.FetchSpeciesModelsWithQuery(stateConfig));

	}

	/**
	 * Triggered when a filter is applied
	 */
	onCollectionFilter($event: { isClean?: boolean, filterFormGroup: FormGroup }) {

		this.filterConditions = SpeciesModel.convertFilterToCondition($event.filterFormGroup);


		let stateConfig: PassableStateConfig<SpeciesModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};


		this.store.dispatch(new modelAction.FetchSpeciesModelsWithQuery(stateConfig));

	}

	/**
	 * Triggered when type or hit the enter in the search box
	 * Do http request to fetch the search results
	 */
	onCollectionSearch(searchText: string) {

		this.searchConditions = this.searchFields.map((field) => ({
			path: field,
			operation: QueryOperation.CONTAINS,
			value: searchText
		}));


		let stateConfig: PassableStateConfig<SpeciesModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};


		this.store.dispatch(new modelAction.FetchSpeciesModelsWithQuery(stateConfig));

	}

	/**
	 * Triggered when there is a new request for first page.
	 */
	onFirstPageRequested() {

		this.pageIndex = 0;


		let stateConfig: PassableStateConfig<SpeciesModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};


		this.store.dispatch(new modelAction.FetchSpeciesModelsWithQuery(stateConfig));

	}

	/**
	 * Triggered when there is a new request for previous page.
	 */
	onPrevPageRequested() {

		this.pageIndex -= 1;


		let stateConfig: PassableStateConfig<SpeciesModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};


		this.store.dispatch(new modelAction.FetchSpeciesModelsWithQuery(stateConfig));

	}

	/**
	 * Triggered when there is a new request for next page.
	 */
	onNextPageRequested() {

		this.pageIndex += 1;


		let stateConfig: PassableStateConfig<SpeciesModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};


		this.store.dispatch(new modelAction.FetchSpeciesModelsWithQuery(stateConfig));

	}

	/**
	 * Triggered when there is a new request for last page.
	 */
	onLastPageRequested() {

		this.pageIndex = Math.floor((this.speciesCount - 1) / this.pageSize);


		let stateConfig: PassableStateConfig<SpeciesModel> = {
			queryParams: this.queryParams,
			collectionId: this.collectionId
		};


		this.store.dispatch(new modelAction.FetchSpeciesModelsWithQuery(stateConfig));

	}

}
