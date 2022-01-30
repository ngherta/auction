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
import AuctionService from "../../../services/auction.service"
import {ExcelExportModule} from '@ag-grid-enterprise/excel-export';

export default {
  name: "AuctionTable",
  components: {
    AgGridVue,
  },

  methods: {
    exportExcel() {
      console.log("Export excel");
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
      let link = document.createElement('router-link');
      link.href = 'http://localhost:8081/auction/' + params.data.id;
      link.innerText = params.data.title;
      link.addEventListener('click', (e) => {
        e.preventDefault();
      });
      return link;
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
          AuctionService.delete(params.data.id).then(
              () => {
                params.api.applyTransaction({
                  remove: [params.node.data],
                });
              },
              (error) => {
                this.rowData = error.error;
              }
          );
        }

        if (action === 'update') {
          AuctionService.update(params.node.data).then(
              () => {
                params.api.stopEditing(false);
              },
              (error) => {
                console.log(error)
                this.rowData = error.error;
              }
          );
        }

        if (action === 'cancel') {
          console.log("NGH - CANCEL");

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
      AuctionService.findAll().then(
          (response) => {
            this.rowData = response.data;
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
      paginationPageSize: 5
    };
  },
  beforeMount() {
    this.gridOptions = {};
    this.editType = 'fullRow';
    this.suppressClickEdit = true;
    this.columnDefs = [
      {headerName: 'Id', field: 'id', sortable: true, filter: true},
      {
        headerName: 'Title', field: 'title', sortable: true, filter: true,
        cellRenderer: this.creatLink
      },
      {headerName: 'Description', field: 'description', sortable: true, filter: true},
      {headerName: 'Status', field: 'statusType', sortable: true, filter: true,},
      {headerName: 'Charity Percent', field: 'charityPercent', sortable: true, filter: true},
      {headerName: 'Start price', field: 'startPrice', sortable: true, filter: true},
      {headerName: 'Finish price', field: 'finishPrice', sortable: true, filter: true},
      {headerName: 'Start date', field: 'startDate', sortable: true, filter: true},
      {headerName: 'Finish date', field: 'finishDate', sortable: true, filter: true},
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

<style scoped>

</style>