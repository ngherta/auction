<template>
  <div v-if="editor">
    <bubble-menu
        class="bubble-menu"
        :tippy-options="{ duration: 100 }"
        :editor="editor"
    >
      <button type="button" @click="editor.chain().focus().toggleBold().run()"
              :class="{ 'is-active': editor.isActive('bold') }">
        Bold
      </button>
      <button type="button" @click="editor.chain().focus().toggleItalic().run()"
              :class="{ 'is-active': editor.isActive('italic') }">
        Italic
      </button>
      <button type="button" @click="editor.chain().focus().toggleStrike().run()"
              :class="{ 'is-active': editor.isActive('strike') }">
        Strike
      </button>
    </bubble-menu>

    <floating-menu
        class="floating-menu"
        :tippy-options="{ duration: 100 }"
        :editor="editor"
    >
      <button type="button" @click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
              :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }">
        H1
      </button>
      <button type="button" @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
              :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }">
        H2
      </button>
      <button type="button" @click="editor.chain().focus().toggleBulletList().run()"
              :class="{ 'is-active': editor.isActive('bulletList') }">
        Bullet List
      </button>
    </floating-menu>
  </div>

  <editor-content :editor="editor"
                  @input="this.$emit('handleChangeValue', editor.getHTML())"
                  class="editor-control"/>
</template>

<script>
import {BubbleMenu, Editor, EditorContent, FloatingMenu} from '@tiptap/vue-3';
import StarterKit from '@tiptap/starter-kit';

export default {
  name: "Editor",
  components: {
    EditorContent,
    BubbleMenu,
    FloatingMenu,
  },
  data() {
    return {
      editor: null,
      text: ""
    }
  },
  mounted() {
    this.editor = new Editor({
      extensions: [
        StarterKit,
      ],
      content: ` `,
    })
  },

  beforeUnmount() {
    this.editor.destroy()
  },
}
</script>

<style lang="scss">

.editor-control {
  &:hover,
  &.is-active {
    opacity: 1;
  }
}

.ProseMirror {
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  color: #495057;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  border-radius: 0.25rem;
  transition: border-color .15s ease-in-out, box-shadow .15s ease-in-out;

  > * + * {
    margin-top: 0.75em;
  }

  ul,
  ol {
    padding: 0 1rem;
  }

  blockquote {
    padding-left: 1rem;
    border-left: 2px solid rgba(#0D0D0D, 0.1);
  }
}

.ProseMirror-focused {
  color: #495057;
  background-color: #fff;
  outline: #80bdff 0;
  box-shadow: 0 0 0 0.2rem rgb(0 123 255 / 25%);
}

.bubble-menu {
  display: flex;
  background-color: #0D0D0D;
  padding: 0.2rem;
  border-radius: 0.5rem;

  button {
    border: none;
    background: none;
    color: #FFF;
    font-size: 0.85rem;
    font-weight: 500;
    padding: 0 0.2rem;
    opacity: 0.6;

    &:hover,
    &.is-active {
      opacity: 1;
    }
  }
}

.floating-menu {
  display: flex;
  background-color: #0D0D0D10;
  padding: 0.2rem;
  border-radius: 0.5rem;

  button {
    border: none;
    background: none;
    font-size: 0.85rem;
    font-weight: 500;
    padding: 0 0.2rem;
    opacity: 0.6;

    &:hover,
    &.is-active {
      opacity: 1;
    }
  }
}
</style>