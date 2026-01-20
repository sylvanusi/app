package com.more.app.base.ui.configuration;

import java.util.ArrayList;
import java.util.List;

import com.more.app.entity.Product;
import com.more.app.entity.ProductTypeEvent;
import com.more.app.entity.ProductTypeEventPolicy;
import com.more.app.entity.enums.QueueType;
import com.more.app.entity.product.ProductWorkFlowQueue;
import com.more.app.repository.productsetup.ProductTypeEventPolicyRepository;
import com.more.app.repository.productsetup.ProductTypeEventRepository;
import com.more.app.repository.productsetup.ProductWorkFlowQueueRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.TextRenderer;

public class WorkflowConfigurationView extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private Binder<Product> binder = new Binder<>(Product.class);
	private Button addBtn;
	private Button removeBtn;

	private H5 queueFieldlbl = new H5("Workflow Queues");
	private H5 eventlbl = new H5("Events");
	private RadioButtonGroup<ProductTypeEvent> eventRB;
	private Grid<ProductWorkFlowQueue> workFlowGrid;
	private ProductTypeEventPolicyField policyField;
	private Product entity;

	private ProductWorkFlowQueueRepository repository;
	private ProductTypeEventRepository eventRepository;
	private ProductTypeEventPolicyRepository policyRepo;

	public WorkflowConfigurationView() {
		eventlbl.getElement().getStyle().set("font-weight", "bold");
		queueFieldlbl.getElement().getStyle().set("font-weight", "bold");

		eventRB = new RadioButtonGroup<>();
		eventRB.setRenderer(new TextRenderer<>(ProductTypeEvent::getEventDescription));
		eventRB.addThemeVariants(RadioGroupVariant.AURA_HORIZONTAL);

		addBtn = new Button("Add Queue");
		removeBtn = new Button("Remove Queue");

		policyField = new ProductTypeEventPolicyField();
		policyField.setVisible(false);

		workFlowGrid = new Grid<ProductWorkFlowQueue>();
		workFlowGrid.addColumn(ProductWorkFlowQueue::getQueueName).setHeader("Queue");
		workFlowGrid.addColumn(k -> k.getQueueType().getQueue()).setHeader("Type");
		workFlowGrid.addColumn(ProductWorkFlowQueue::getPolicyName).setHeader("Policy");
		workFlowGrid.addColumn(ProductWorkFlowQueue::getFlowSequence).setHeader("Sequence")
				.setComparator(ProductWorkFlowQueue::getFlowSequence);
		workFlowGrid.addColumn(ProductWorkFlowQueue::isFirstQueue).setHeader("First Queue");
		workFlowGrid.addColumn(ProductWorkFlowQueue::isFinalQueue).setHeader("Last Queue");
		workFlowGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_COMPACT);
		workFlowGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		workFlowGrid.getElement().getStyle().set("border", "0.5px solid #e6f5ff");
		workFlowGrid.getClassNames().add("appgrid");
		workFlowGrid.setSelectionMode(SelectionMode.SINGLE);
		workFlowGrid.setPageSize(10);
		workFlowGrid.setMaxHeight("250px");

		Hr hr1 = new Hr();
		hr1.setSizeFull();
		Hr hr2 = new Hr();
		hr2.setSizeFull();

		add(eventlbl, hr1, eventRB, hr2, queueFieldlbl, addBtn, policyField, workFlowGrid, removeBtn);

		eventRB.addValueChangeListener(event -> {
			loadWorkflowGrid();
		});

		addBtn.addClickListener(evt -> {
			ProductWorkFlowQueue queue = new ProductWorkFlowQueue();
			queue.setProduct(entity);
			queue.setProductId(entity.getId());
			queue.setEvent(eventRB.getValue());
			queue.setEventId(eventRB.getValue().getId());
			queue.setFlowSequence(
					repository.findByProductIdAndEventId(entity.getId(), eventRB.getValue().getId()).size() + 1);
			policyField.setEntity(queue);
			addBtn.setEnabled(false);
			policyField.setVisible(true);
		});

		workFlowGrid.addSelectionListener(evt -> {
			if (evt.getFirstSelectedItem().isPresent()) {
				int size = workFlowGrid.getDataCommunicator().getItemCount();
				ProductWorkFlowQueue itemSelected = evt.getFirstSelectedItem().get();
				removeBtn.setEnabled(itemSelected.getFlowSequence() == size);
			} else
				removeBtn.setEnabled(false);

		});

		removeBtn.addClickListener(evt -> {
			if (!workFlowGrid.getSelectedItems().isEmpty()) {
				Object[] arr = workFlowGrid.getSelectedItems().toArray();

				ProductWorkFlowQueue itemSelected = (ProductWorkFlowQueue) arr[0];
				repository.delete(itemSelected);
				loadWorkflowGrid();
			}

		});
		setSpacing(true);
		// setPadding(true);
		setMargin(true);
	}

	private void loadWorkflowGrid() {
		workFlowGrid.setItems(new ArrayList<ProductWorkFlowQueue>());
		workFlowGrid.getDataCommunicator().getKeyMapper().removeAll();
		workFlowGrid.setItems(repository.findByProductIdAndEventId(entity.getId(), eventRB.getValue().getId()));
		workFlowGrid.getDataCommunicator().reset();
	}

	public void updateFields(Product product) {
		this.entity = product;
		if (entity != null) {
			binder.setBean(product);

			List<ProductTypeEvent> evtList = eventRepository.findByProductTypeId(product.getType().getId());
			eventRB.setItems(evtList);
			eventRB.setValue(evtList.get(0));

			List<ProductWorkFlowQueue> queueList = repository.findByProductIdAndEventId(product.getId(),
					eventRB.getValue().getId());
			workFlowGrid.setItems(queueList);
		}
	}

	class ProductTypeEventPolicyField extends HorizontalLayout {
		private Button addQueuebtn;
		private TextField queuenameTF;
		private ComboBox<QueueType> queueTypeCb;
		private ComboBox<ProductTypeEventPolicy> eventPolicyCb;
		private Checkbox cbFirstQueue = new Checkbox("Input Queue");
		private Checkbox cbFinalQueue = new Checkbox("Final Queue");
		private Binder<ProductWorkFlowQueue> binder = new Binder<>(ProductWorkFlowQueue.class);
		private ProductWorkFlowQueue wfEntity;
		private ProductTypeEventPolicyField field;

		ProductTypeEventPolicyField() {
			this.field = this;
			queuenameTF = new TextField();
			queueTypeCb = new ComboBox<>();
			queueTypeCb.setItemLabelGenerator(QueueType::getQueue);
			eventPolicyCb = new ComboBox<>();
			eventPolicyCb.setItemLabelGenerator(ProductTypeEventPolicy::getPolicyName);

			queuenameTF.setLabel("Queue Name");
			queueTypeCb.setLabel("Queue Type");
			eventPolicyCb.setLabel("Policy");

			setMargin(true);
			setSpacing(true);

			addQueuebtn = new Button("Add");

			add(queuenameTF, queueTypeCb, eventPolicyCb, cbFirstQueue, cbFinalQueue, addQueuebtn);
			setVerticalComponentAlignment(Alignment.BASELINE, queuenameTF, queueTypeCb, eventPolicyCb, cbFirstQueue, cbFinalQueue, addQueuebtn);


			queueTypeCb.addValueChangeListener(event -> {
				eventPolicyCb.clear();
				if (null != event.getValue()) {
					if (getWfEntity().getFlowSequence() == 1) {
						eventPolicyCb.setItems(
								policyRepo.findByEventIdAndInputQueueType(getWfEntity().getEvent().getId(), true));
						cbFirstQueue.setValue(true);
						cbFirstQueue.setEnabled(false);
						cbFinalQueue.setValue(false);
						cbFinalQueue.setEnabled(false);
						
					} else {
						eventPolicyCb.setItems(
								policyRepo.findByEventIdAndInputQueueType(getWfEntity().getEvent().getId(), false));
						cbFirstQueue.setValue(false);
						cbFirstQueue.setEnabled(false);
						cbFinalQueue.setEnabled(true);
					}
				}
			});

			addQueuebtn.addClickListener(event -> {
				if (binder.validate().isOk()) {

					ProductWorkFlowQueue entity = binder.getBean();
					entity.setPolicyId(entity.getPolicy().getId());
					repository.save(entity);
					loadWorkflowGrid();
					addQueuebtn.setEnabled(false);
					addBtn.setEnabled(true);
					this.setVisible(false);
				}
			});

			binder.forField(queuenameTF).asRequired().bind("queueName");
			binder.forField(queueTypeCb).asRequired().bind("queueType");
			binder.forField(eventPolicyCb).asRequired().bind("policy");
			binder.forField(cbFirstQueue).asRequired().bind("finalQueue");
			binder.forField(cbFinalQueue).asRequired().bind("finalQueue");
		}

		void setEntity(ProductWorkFlowQueue wfEntity) {
			setWfEntity(wfEntity);
			binder.setBean(wfEntity);
			if (wfEntity.getFlowSequence() == 1) {
				queueTypeCb.setItems(QueueType.inputQueueType());
			} else {
				queueTypeCb.setItems(QueueType.nonInputQueueType());
			}
			addQueuebtn.setEnabled(true);
		}

		/**
		 * @return the wfEntity
		 */
		public ProductWorkFlowQueue getWfEntity() {
			return wfEntity;
		}

		/**
		 * @param wfEntity the wfEntity to set
		 */
		public void setWfEntity(ProductWorkFlowQueue wfEntity) {
			this.wfEntity = wfEntity;
		}
	}

	public void setRepository(ProductWorkFlowQueueRepository repository) {
		this.repository = repository;
	}

	public void setEventRepository(ProductTypeEventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	public void setPolicyRepo(ProductTypeEventPolicyRepository policyRepo) {
		this.policyRepo = policyRepo;
	}

}
