<template>
  <cropper
      :src="img"

      @change="change"
  />
  <div class="cl-upload">
    <!-- create a form that will not submit to a server but will prevent submit and
    use the upload function as a handle-->
    <form v-on:submit.prevent="upload">
      <!-- allow the user to select an image file and when they have selected it call a function
      to handle this event-->
      <label for="file-input">Upload:</label>
      <input
          id="file-input"
          type="file"
          accept="image/png, image/jpeg"
          @change="handleFileChange($event)"
      />
      <!-- submit button is disabled until a file is selected -->
      <button type="submit" :disabled="filesSelected < 1">Upload</button>
      <div class="progress">
        <div class="progress-bar"
             role="progressbar"
             :style="'width: ' + this.progress + '%'"
             :aria-valuenow="this.progress"
             aria-valuemin="0"
             aria-valuemax="100">
        </div>
      </div>
    </form>

    <!-- display uploaded image if successful -->
    <div v-if="results">
      <section v-for="result in results" :key="result">
        <img :src="result.secure_url" :alt="result.public_id"/>
      </section>
    </div>

    <!-- display errors if not successful -->
    <section>
      <ul v-if="errors.length > 0">
        <li v-for="(error,index) in errors" :key="index">{{ error }}</li>
      </ul>
    </section>
  </div>
</template>

<script>
import axios from "axios";
import { Cropper } from 'vue-advanced-cropper';
import 'vue-advanced-cropper/dist/style.css';

export default {
  name: "CloudinaryUpload",
  components: {
    Cropper,
  },
  data() {
    const progressBarOptions = {
      text: {
        shadowColor: "black",
        fontSize: 14,
        fontFamily: "Helvetica",
        dynamicPosition: true
      },
      progress: {
        color: "#E8C401",
        backgroundColor: "#000000"
      },
      layout: {
        height: 35,
        width: 140,
        type: "line",
        progressPadding: 0,
        verticalTextAlign: 63
      }
    };
    return {
      results: [],
      errors: [],
      file: null,
      filesSelected: 0,
      img: 'https://images.pexels.com/photos/4323307/pexels-photo-4323307.jpeg',
      cloudName: "dxn6dcenz",
      preset: "jhjwl2sq",
      tags: "browser-upload",
      progress: 0,
      showProgress: false,
      options: progressBarOptions,
      fileContents: null,
      formData: null
    };
  },
  methods: {
    change({ coordinates, canvas }) {
      console.log(coordinates, canvas);
    },
    // function to handle file info obtained from local
    // file system and set the file state
    handleFileChange: function (event) {
      //returns an array of files even though multiple not used
      this.file = event.target.files[0];
      this.filesSelected = event.target.files.length;
    },
    prepareFormData: function () {
      this.formData = new FormData();
      this.formData.append("upload_preset", this.preset);
      this.formData.append("tags", this.tags); // Optional - add tag for image admin in Cloudinary
      this.formData.append("file", this.fileContents);
    },
    // function to handle form submit
    upload() {
      this.progress = 0;
      //no need to look at selected files if there is no cloudname or preset

      let reader = new FileReader();
      // attach listener to be called when data from file
      reader.addEventListener(
          "load",
          function () {
            this.fileContents = reader.result;
            this.prepareFormData();
            let cloudinaryUploadURL = `https://api.cloudinary.com/v1_1/${this.cloudName}/upload`;
            let requestObj = {
              url: cloudinaryUploadURL,
              method: "POST",
              data: this.formData,
              onUploadProgress: function (progressEvent) {
                this.progress = Math.round(
                    (progressEvent.loaded * 100) / progressEvent.total
                );
                //bind "this" to access vue state during callback
              }.bind(this)
            };
            //show progress bar at beginning of post
            this.showProgress = true;
            axios(requestObj)
                .then(response => {
                  this.results.push(response.data);
                  this.$emit('uploadNewImages', response.data.secure_url)
                  // this.$store.dispatch("image/uploadImage", this.results);
                })
                .catch(error => {
                  this.errors.push(error);
                })
                .finally(() => {
                  setTimeout(
                      function () {
                        this.showProgress = false;
                      }.bind(this),
                      1000
                  );
                });
          }.bind(this),
          false
      );
      // call for file read if there is a file
      if (this.file && this.file.name) {
        reader.readAsDataURL(this.file);
      }
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
form {
  display: grid;
  padding: 1em;
  background: #f9f9f9;
  border: 1px solid #c1c1c1;
  margin: 2rem auto 0 auto;
  max-width: 500px;
  padding: 1em;
}

form input {
  background: #fff;
  border: 1px solid #9c9c9c;
}

form button {
  background-color: blue;
  color: white;
  font-size: 1em;
  font-weight: bold;
  padding: 0.7em;
  width: 100%;
  border: 0;
}

form button:hover {
  background: gold;
  color: black;
}

label {
  padding: 0.5em 0.5em 0.5em 0;
}

input {
  padding: 0.7em;
  margin-bottom: 0.5rem;
}

input:focus {
  outline: 3px solid gold;
}

@media (min-width: 400px) {
  form {
    grid-template-columns: 150px 1fr;
    grid-gap: 16px;
  }

  label {
    text-align: right;
    grid-column: 1 / 2;
  }

  input,
  button,
  .progress{
    grid-column: 2 / 3;
  }
}

button {
  background-color: blue;
  color: white;
  font-weight: bold;
  border-radius: 10px;
}

button:focus {
  outline: none;
}

form button:disabled,
form button[disabled] {
  border: 1px solid #999999;
  background-color: #cccccc;
  color: #666666;
}

section {
  margin: 10px 0;
}

img {
  max-width: 300px;
  height: auto;
}
</style>