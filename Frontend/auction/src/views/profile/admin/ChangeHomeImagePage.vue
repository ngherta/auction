<template>
  <div v-if="isActiveComponent" class="container mb-5">
    <div class="border p-5">
      <h2 class="h1 mb-3">Add new image to home page</h2>
      <Form @submit="handleUpdate" :validation-schema="schema">
        <div class="row align-items-center">
          <div class="col">
            <div class="mb-3">
              <label for="link">LINK:</label>
              <Field v-model="linkModel"
                     name="link"
                     id="link"
                     type="text"
                     class="form-control"/>
              <ErrorMessage name="link" class="error-feedback"/>
            </div>
            <div class="mb-3">
              <label for="sequence">SEQUENCE:</label>
              <Field v-model="sequenceModel"
                     name="sequence"
                     id="sequence"
                     type="number"
                     class="form-control"/>
              <ErrorMessage name="sequence" class="error-feedback"/>
            </div>

          </div>
          <div class="col">
            <UploadImage show-results="false"
                         ratio="1/3"
                         width="1300"
                         height="500"
                         @uploadNewImages="uploadNewImages($event)"/>
          </div>
        </div>
        <div class="row mt-5">
          <button class="btn btn-primary btn-block" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
            CREATE
          </button>
        </div>
        <div class="row mt-3">
          <button type="button"
                  @click="addImageForPreview"
                  class="btn btn-info btn-block"
                  data-toggle="modal"
                  data-target="#descriptionModalLong">
            PREVIEW
          </button>

          <div class="custom-table mt-5">
            <table class="table table-bordered table-striped">
              <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">Url</th>
                <th scope="col">Image</th>
                <th scope="col">Sequence</th>
              </tr>
              </thead>
              <tbody class="">
              <tr v-for="(image, index) of this.sortImages()"
                  :key="index"
                  class="border-top border-bottom"
                  v-bind:class="{
                  firstTableRow : index == 0
                }">
                <td>{{ image.id }}</td>
                <td v-if="image.isInternalLink == true">
                  <router-link :to="image.url">{{ image.url }}</router-link>
                </td>
                <td v-else-if="image.isInternalLink == false">
                  <a :href="image.url">{{ image.url }}</a>
                </td>
                <td>
                  <img :src="image.imageLink" class="img-thumbnail">
                </td>
                <th scope="row">{{ image.sequence }}</th>
              </tr>
              </tbody>
            </table>
          </div>

          <!-- Modal -->
          <div class="modal fade" id="descriptionModalLong" tabindex="-1" role="dialog"
               aria-labelledby="descriptionModalLongTitle" aria-hidden="true">
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close close-custom" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                  <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner">
                      <div v-for="(image, index) of this.imagesPreview"
                           :key="image"
                           class="carousel-item "
                           v-bind:class="{active : index == 0}">
                        <router-link :to="image.url">
                          <img class="d-block w-100"
                               v-bind:src="image.imageLink" :alt="index + 'slide'">
                        </router-link>
                      </div>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                      <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                      <span class="carousel-control-next-icon" aria-hidden="true"></span>
                      <span class="sr-only">Next</span>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </Form>
    </div>
  </div>
</template>

<script>
import UploadImage from "@/views/UploadImage";
import ImageLinkService from '../../../services/imageLink.service';
import {ErrorMessage, Field, Form} from "vee-validate";
import * as yup from "yup";

export default {
  name: "ChangeHomeImagePage",
  components: {
    Form,
    Field,
    ErrorMessage,
    UploadImage,
  },
  data() {
    const schema = yup.object().shape({
      link: yup
          .string()
          .required("Link is required!")
          .min(5, "Must be at least 3 characters!"),
      sequence: yup
          .string()
          .required("Sequence is required!")
          .min(1, "Must be at least 3 characters!")
    })

    return {
      images: [],
      imagesPreview: [],
      sequenceArr: [''],
      loading: false,
      imageUploaded: null,
      linkModel: null,
      sequenceModel: null,
      isActiveComponent: true,
      schema
    }
  },
  methods: {
    addImageForPreview() {
      if (this.sequenceModel == null ||
          this.linkModel == null ||
          this.imageUploaded == null) {
        return;
      }

      if (this.imagesPreview[this.imagesPreview.length - 1].id == null) {
        this.imagesPreview.pop();
      }

      const previewImage = {
        id: null,
        url: this.linkModel,
        sequence: this.sequenceModel,
        imageLink: this.imageUploaded,
      }
      this.imagesPreview.push(previewImage);
      this.imagesPreview = this.sortImagesForPreview();
    },
    uploadNewImages(event) {
      this.imageUploaded = event;
    },
    sequenceValidationApi(value) {
      if (this.sequenceArr.contains(value)) {
        return true;
      }
      return false;
    },
    getImageLinks() {
      ImageLinkService.getAllByType('HOME_PAGE').then(
          (response) => {
            this.images = response.data;
            this.imagesPreview = response.data;
            for (let i = 0; i < response.data.length; i++) {
              this.sequenceArr.push(response.data[i].sequence);
            }
          },
          (error) => {
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            });
          }
      )
    },
    handleUpdate(data) {
      this.loading = true;
      if (this.imageUploaded == null) {
        this.loading = false;
        this.$notify({
          text: 'You should add image!',
          type: 'error'
        });
        return;
      }

      const request = {
        url: this.linkModel,
        imageLink: this.imageUploaded,
        sequence: data.sequence
      }

      if (this.linkModel == null) {
        this.$notify({
          text: 'You should add link!',
          type: 'error'
        });
        return;
      }

      if (this.sequenceModel == null) {
        this.$notify({
          text: 'You should add sequence!',
          type: 'error'
        });
        return;
      }
      ImageLinkService.create(request, 'HOME_PAGE').then(
          (response) => {
            console.log(response)
            const responseData = {
              id: response.data.id,
              sequence: response.data.sequence,
              url: response.data.url
            }
            this.images.push(responseData);
            this.loading = false;
          },
          (error) => {
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            })
            this.loading = false;
          }
      )
    },
    sortImages() {
      return this.images.slice().sort(function (a, b) {
        return (a.sequence > b.sequence) ? 1 : -1;
      });
    },
    sortImagesForPreview() {
      return this.imagesPreview.slice().sort(function (a, b) {
        return (a.sequence > b.sequence) ? 1 : -1;
      });
    }
  },
  mounted() {
    this.isActiveComponent = true;
    this.getImageLinks();
  },
  unmounted() {
    this.isActiveComponent = false;
  }
}
</script>

<style scoped>
@media only screen and (min-width: 576px) {
  .modal-dialog {
    max-width: 500px;
    /*margin: 1.75rem auto;*/
  }
}

@media only screen and (min-width: 992px) {
  .modal-dialog {
    max-width: 600px;
  }
}

@media only screen and (min-width: 1200px) {
  .modal-dialog {
    max-width: 700px;
  }
}

@media only screen and (min-width: 1400px) {
  .modal-dialog {
    max-width: 800px;
  }
}

@media only screen and (min-width: 1600px) {
  .modal-dialog {
    max-width: 900px;
  }
}

.error-feedback {
  color: red;
}

.modal-header .close-custom {
  margin: -1rem -1rem -1rem 0;
}
</style>