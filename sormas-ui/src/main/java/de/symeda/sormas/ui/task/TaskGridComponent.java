/*******************************************************************************
 * SORMAS® - Surveillance Outbreak Response Management & Analysis System
 * Copyright © 2016-2018 Helmholtz-Zentrum für Infektionsforschung GmbH (HZI)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package de.symeda.sormas.ui.task;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.ComboBox;

import de.symeda.sormas.api.EntityRelevanceStatus;
import de.symeda.sormas.api.FacadeProvider;
import de.symeda.sormas.api.i18n.Captions;
import de.symeda.sormas.api.i18n.I18nProperties;
import de.symeda.sormas.api.task.TaskCriteria;
import de.symeda.sormas.api.task.TaskDto;
import de.symeda.sormas.api.task.TaskStatus;
import de.symeda.sormas.api.user.UserRight;
import de.symeda.sormas.api.user.UserRole;
import de.symeda.sormas.ui.ControllerProvider;
import de.symeda.sormas.ui.UserProvider;
import de.symeda.sormas.ui.ViewModelProviders;
import de.symeda.sormas.ui.utils.CssStyles;
import de.symeda.sormas.ui.utils.LayoutUtil;

@SuppressWarnings("serial")
public class TaskGridComponent extends VerticalLayout {

	private static final String MY_TASKS = "myTasks";
	private static final String OFFICER_TASKS = "officerTasks";

	private TaskCriteria criteria;

	private TaskGrid grid;    
	private TasksView tasksView;
	private Map<Button, String> statusButtons;
	private Button activeStatusButton;

	// Filter
	private ComboBox statusFilter;
	private Button resetButton;
	private ComboBox relevanceStatusFilter;

	MenuBar bulkOperationsDropdown;

	private VerticalLayout gridLayout;

	private Label viewTitleLabel;
	private String originalViewTitle;

	public TaskGridComponent(Label viewTitleLabel, TasksView tasksView) {
		setSizeFull();
		setMargin(false);

		this.viewTitleLabel = viewTitleLabel;
		this.tasksView = tasksView;
		originalViewTitle = viewTitleLabel.getValue();

		criteria = ViewModelProviders.of(TasksView.class).get(TaskCriteria.class);
		if (criteria.getRelevanceStatus() == null) {
			criteria.relevanceStatus(EntityRelevanceStatus.ACTIVE);
		}

		grid = new TaskGrid(criteria);
		gridLayout = new VerticalLayout();
		gridLayout.addComponent(createFilterBar());
		gridLayout.addComponent(createAssigneeFilterBar());
		gridLayout.addComponent(grid);
		grid.getDataProvider().addDataProviderListener(e -> updateAssigneeFilterButtons());

		gridLayout.setMargin(true);
		styleGridLayout(gridLayout);

		addComponent(gridLayout);
	}

	public HorizontalLayout createFilterBar() {
		HorizontalLayout filterLayout = new HorizontalLayout();
		filterLayout.setMargin(false);
		filterLayout.setSpacing(true);
		filterLayout.setSizeUndefined();

		statusFilter = new ComboBox();
		statusFilter.setWidth(200, Unit.PIXELS);
		statusFilter.setInputPrompt(I18nProperties.getPrefixCaption(TaskDto.I18N_PREFIX, TaskDto.TASK_STATUS));
		statusFilter.addItems((Object[])TaskStatus.values());
		statusFilter.addValueChangeListener(e -> {
			criteria.taskStatus((TaskStatus)e.getProperty().getValue());
			tasksView.navigateTo(criteria);
		});
		filterLayout.addComponent(statusFilter);

		resetButton = new Button(I18nProperties.getCaption(Captions.actionResetFilters));
		resetButton.setVisible(false);
		resetButton.addClickListener(event -> {
			ViewModelProviders.of(TasksView.class).remove(TaskCriteria.class);
			tasksView.navigateTo(null);
		});
		filterLayout.addComponent(resetButton);

		return filterLayout;
	}

	public HorizontalLayout createAssigneeFilterBar() {
		HorizontalLayout assigneeFilterLayout = new HorizontalLayout();
		assigneeFilterLayout.setMargin(false);
		assigneeFilterLayout.setSpacing(true);
		assigneeFilterLayout.setWidth(100, Unit.PERCENTAGE);
		assigneeFilterLayout.addStyleName(CssStyles.VSPACE_3);

		statusButtons = new HashMap<>();

		HorizontalLayout buttonFilterLayout = new HorizontalLayout();
		buttonFilterLayout.setSpacing(true);
		{
			Button allTasks = new Button(I18nProperties.getCaption(Captions.all), e -> processAssigneeFilterChange(null));
			CssStyles.style(allTasks, ValoTheme.BUTTON_BORDERLESS, CssStyles.BUTTON_FILTER);
			allTasks.setCaptionAsHtml(true);
			buttonFilterLayout.addComponent(allTasks);
			statusButtons.put(allTasks, I18nProperties.getCaption(Captions.all));			

			Button officerTasks = new Button(I18nProperties.getCaption(Captions.taskOfficerTasks), e -> processAssigneeFilterChange(OFFICER_TASKS));
			initializeStatusButton(officerTasks, buttonFilterLayout, OFFICER_TASKS, I18nProperties.getCaption(Captions.taskOfficerTasks));
			Button myTasks = new Button(I18nProperties.getCaption(Captions.taskMyTasks), e -> processAssigneeFilterChange(MY_TASKS));
			initializeStatusButton(myTasks, buttonFilterLayout, MY_TASKS, I18nProperties.getCaption(Captions.taskMyTasks));

			// Default filter for lab users (that don't have any other role) is "My tasks"
			if ((UserProvider.getCurrent().hasUserRole(UserRole.LAB_USER) || UserProvider.getCurrent().hasUserRole(UserRole.EXTERNAL_LAB_USER)) && UserProvider.getCurrent().getUserRoles().size() == 1) {
				activeStatusButton = myTasks;
			} else {
				activeStatusButton = allTasks;
			}
		}
		assigneeFilterLayout.addComponent(buttonFilterLayout);

		HorizontalLayout actionButtonsLayout = new HorizontalLayout();
		actionButtonsLayout.setSpacing(true);
		{
			// Show active/archived/all dropdown
			if (UserProvider.getCurrent().hasUserRight(UserRight.TASK_VIEW_ARCHIVED)) {
				relevanceStatusFilter = new ComboBox();
				relevanceStatusFilter.setWidth(140, Unit.PERCENTAGE);
				relevanceStatusFilter.setNullSelectionAllowed(false);
				relevanceStatusFilter.addItems((Object[]) EntityRelevanceStatus.values());
				relevanceStatusFilter.setItemCaption(EntityRelevanceStatus.ACTIVE, I18nProperties.getCaption(Captions.taskActiveTasks));
				relevanceStatusFilter.setItemCaption(EntityRelevanceStatus.ARCHIVED, I18nProperties.getCaption(Captions.taskArchivedTasks));
				relevanceStatusFilter.setItemCaption(EntityRelevanceStatus.ALL, I18nProperties.getCaption(Captions.taskAllTasks));
				relevanceStatusFilter.addValueChangeListener(e -> {
					criteria.relevanceStatus((EntityRelevanceStatus) e.getProperty().getValue());
					tasksView.navigateTo(criteria);
				});
				actionButtonsLayout.addComponent(relevanceStatusFilter);
			}

			// Bulk operation dropdown
			if (UserProvider.getCurrent().hasUserRight(UserRight.PERFORM_BULK_OPERATIONS)) {
				assigneeFilterLayout.setWidth(100, Unit.PERCENTAGE);

				bulkOperationsDropdown = new MenuBar();	
				MenuItem bulkOperationsItem = bulkOperationsDropdown.addItem(I18nProperties.getCaption(Captions.bulkActions), null);

				Command deleteCommand = selectedItem -> {
					ControllerProvider.getTaskController().deleteAllSelectedItems(grid.asMultiSelect().getSelectedItems(), () -> tasksView.navigateTo(criteria));
				};
				bulkOperationsItem.addItem(I18nProperties.getCaption(Captions.bulkDelete), VaadinIcons.TRASH, deleteCommand);
				bulkOperationsDropdown.setVisible(tasksView.getViewConfiguration().isInEagerMode());

				actionButtonsLayout.addComponent(bulkOperationsDropdown);
			}
		}
		assigneeFilterLayout.addComponent(actionButtonsLayout);
		assigneeFilterLayout.setComponentAlignment(actionButtonsLayout, Alignment.TOP_RIGHT);
		assigneeFilterLayout.setExpandRatio(actionButtonsLayout, 1);

		return assigneeFilterLayout;
	}

	private void styleGridLayout(VerticalLayout gridLayout) {
		gridLayout.setSpacing(false);
		gridLayout.setSizeFull();
		gridLayout.setExpandRatio(grid, 1);
		gridLayout.setStyleName("crud-main-layout");
	}

	private void processAssigneeFilterChange(String assignee) {
		if (OFFICER_TASKS.equals(assignee)) {
			criteria.assigneeUser(null);
			criteria.excludeAssigneeUser(FacadeProvider.getUserFacade().getCurrentUserAsReference());
		} else if (MY_TASKS.equals(assignee)) {
			criteria.excludeAssigneeUser(null);
			criteria.assigneeUser(FacadeProvider.getUserFacade().getCurrentUserAsReference());
		} else {
			criteria.excludeAssigneeUser(null);
			criteria.assigneeUser(null);
		}

		tasksView.navigateTo(criteria);
	}

	private void initializeStatusButton(Button button, HorizontalLayout filterLayout, String status, String caption) {
		button.setData(status);
		CssStyles.style(button, ValoTheme.BUTTON_BORDERLESS, CssStyles.BUTTON_FILTER, CssStyles.BUTTON_FILTER_LIGHT);
		button.setCaptionAsHtml(true);
		filterLayout.addComponent(button);
		statusButtons.put(button, caption);
	}

	public void reload(ViewChangeEvent event) {
		String params = event.getParameters().trim();
		if (params.startsWith("?")) {
			params = params.substring(1);
			criteria.fromUrlParams(params);
		}
		updateFilterComponents();
		grid.reload();
	}

	private void updateAssigneeFilterButtons() {
		statusButtons.keySet().forEach(b -> {
			CssStyles.style(b, CssStyles.BUTTON_FILTER_LIGHT);
			b.setCaption(statusButtons.get(b));
			if ((OFFICER_TASKS.equals(b.getData()) && criteria.getExcludeAssigneeUser() != null)
					|| (MY_TASKS.equals(b.getData()) && criteria.getAssigneeUser() != null)) {
				activeStatusButton = b;
			}
		});
		if (activeStatusButton != null) {
			CssStyles.removeStyles(activeStatusButton, CssStyles.BUTTON_FILTER_LIGHT);
			activeStatusButton.setCaption(statusButtons.get(activeStatusButton) 
					+ LayoutUtil.spanCss(CssStyles.BADGE, String.valueOf(grid.getItemCount())));
		}
	}

	public void updateFilterComponents() {
		// TODO replace with Vaadin 8 databinding
		tasksView.setApplyingCriteria(true);

		resetButton.setVisible(criteria.hasAnyFilterActive());

		updateAssigneeFilterButtons();		
		if (relevanceStatusFilter != null) {
			relevanceStatusFilter.setValue(criteria.getRelevanceStatus());
		}
		statusFilter.setValue(criteria.getTaskStatus());

		tasksView.setApplyingCriteria(false);
	}

	public TaskGrid getGrid() {
		return grid;
	}

	public MenuBar getBulkOperationsDropdown() {
		return bulkOperationsDropdown;
	}

	public TaskCriteria getCriteria() {
		return criteria;
	}

}
