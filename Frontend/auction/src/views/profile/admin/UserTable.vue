<template>
  <div class="container">
    <div>
      <button class="btn btn-primary" v-on:click="exportExcel">
        Export to excel!
      </button>
    </div>
    <div>
      <ag-grid-vue
          style="width: 100%; height: 600px;"
          class="ag-theme-alpine"
          id="myGrid"
          @grid-ready="onGridReady"
          @cell-clicked="onCellClicked"
          @row-editing-started="onRowEditingStarted"
          @row-editing-stopped="onRowEditingStopped"
          :gridOptions="gridOptions"
          :columnDefs="columnDefs"
          :defaultColDef="defaultColDef"
          :animateRows="true"
          :animateColumns="true"
          :pagination=true
          :rowData="rowData"
          :modules="modules"
          :paginationPageSize="paginationPageSize"
          :suppressPaginationPanel="true"
          :editType="this.editType"
          :suppressClickEdit="this.suppressClickEdit"
      ></ag-grid-vue>
    </div>
  </div>
</template>

<script>
import "@ag-grid-community/core/dist/styles/ag-grid.css";
import "@ag-grid-community/core/dist/styles/ag-theme-alpine.css";
import {AgGridVue} from "ag-grid-vue3";
import UserService from "../../../services/user.service";
import {ExcelExportModule} from '@ag-grid-enterprise/excel-export';

export default {
  name: "UserTable",
  components: {
    AgGridVue,
  },

  methods: {
    exportExcel() {
      this.gridApi.exportDataAsExcel();
    },
    actionCellRenderer(params) {
      let eGui = document.createElement('div');

      let editingCells = params.api.getEditingCells();
// checks if the rowIndex matches in at least one of the editing cells
      let isCurrentRowEditing = editingCells.some((cell) => {
        return cell.rowIndex === params.node.rowIndex;
      });

      if (isCurrentRowEditing) {
        eGui.innerHTML = `
<button type="button" class="btn btn-success" data-action="update"> update</button>
<button type="button" class="btn btn-secondary" data-action="cancel"> cancel</button>
`;
      } else {
        eGui.innerHTML = `
<button type="button" class="btn btn-primary" data-action="edit"> edit</button>
<button type="button" class="btn btn-danger" data-action="delete"> delete</button>
`;
      }

      return eGui;
    },
    creatLink(params) {
      let link = document.createElement('a');
      link.href = 'http://34.140.181.128:8082/user/' + params.data.id;
      link.innerText = params.data.email;
      link.addEventListener('click', (e) => {
        e.preventDefault();
      });
      return link;
      // return 'Value is **' + params.value.email + '**';
    },
    reRenderUserStatus(params) {
      if (params.data.enabled == true) {
        return 'Enabled';
      } else if (params.data.enabled == false) {
        return 'Disabled'
      }
    },
    onCellClicked(params) {
// Handle click event for action cells
      if (
          params.column.colId === 'action' &&
          params.event.target.dataset.action
      ) {
        let action = params.event.target.dataset.action;

        if (action === 'edit') {
          params.api.startEditingCell({
            rowIndex: params.node.rowIndex,
// gets the first columnKey
            colKey: params.columnApi.getDisplayedCenterColumns()[0].colId,
          });
        }

        if (action === 'delete') {
          UserService.delete(params.data.id).then(
              () => {
                params.api.applyTransaction({
                  remove: [params.node.data],
                });
              },
              (error) => {
                this.rowData = error.message();
              }
          );
        }

        if (action === 'update') {
          if (params.node.data.enabled == "Enabled") {
            params.node.data.enabled = true;
          } else if (params.node.data.enabled == "Disabled") {
            params.node.data.enabled = false;
          }
          UserService.update(params.node.data).then(
              () => {
                params.api.stopEditing(false);
              },
              (error) => {
                this.$notify({
                  text: error.response.data.errorMessage,
                  type: 'error'
                });
              }
          );
        }

        if (action === 'cancel') {
          params.api.stopEditing(true);
        }
      }
    },

    onRowEditingStarted: (params) => {
      params.api.refreshCells({
        columns: ['action'],
        rowNodes: [params.node],
        force: true,
      });
    },
    onRowEditingStopped: (params) => {
      params.api.refreshCells({
        columns: ['action'],
        rowNodes: [params.node],
        force: true,
      });
    },

    onGridReady() {
      UserService.getAllUsers().then(
          (response) => {
            this.rowData = response.data.content;
          },
          (error) => {
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            });
          }
      );
    },
  },
  data: function () {
    return {
      gridOptions: null,
      gridApi: null,
      columnApi: null,
      columnDefs: null,
      defaultColDef: null,
      rowData: null,
      modules: [ExcelExportModule],
      paginationPageSize: 25
    };
  },
  beforeMount() {
    this.gridOptions = {};
    this.editType = 'fullRow';
    this.suppressClickEdit = true;
    this.columnDefs = [
      {field: 'id', minWidth: 150, sortable: true, filter: true},
      {field: 'firstName', minWidth: 90, sortable: true, filter: true},
      {field: 'lastName', minWidth: 90, sortable: true, filter: true},
      {
        field: 'email', minWidth: 90, sortable: true, filter: true,
        cellRenderer: this.creatLink
      },
      {field: 'birthday', minWidth: 90, sortable: true, filter: true},
      {
        field: 'enabled', minWidth: 90, sortable: true, filter: true,
        cellRenderer: this.reRenderUserStatus,
        cellEditor: 'agSelectCellEditor',
        cellEditorParams: {
          values: ['Enabled', 'Disabled'],
        },
      },

      {
        headerName: 'action',
        minWidth: 150,
        cellRenderer: this.actionCellRenderer,
        editable: false,
        colId: 'action',
      },
    ];
    this.defaultColDef = {
      editable: true,
    };
  },
  mounted() {
    this.gridApi = this.gridOptions.api;
    this.gridColumnApi = this.gridOptions.columnApi;
  }
}
</script>
