import { style } from '../styler.js';

style(`
  .app-bar {
    background-color: var(--lumo-base-color);
    box-shadow: var(--lumo-box-shadow-s);
    flex-direction: column;
    position: relative;
    z-index: 1;
  }

  /* Container */
  .app-bar__container {
    padding: 0 var(--lumo-space-r-l);
    transition: padding var(--transition-duration-m);
  }

  /* Navi icon */
  .app-bar__navi-icon,
  .app-bar__context-icon {
    margin-right: var(--lumo-space-l);
    margin-bottom: calc((var(--app-bar-height-mobile) - var(--lumo-icon-size-m)) / 2);
    margin-top: calc((var(--app-bar-height-mobile) - var(--lumo-icon-size-m)) / 2);
  }

  /* Title */
  .app-bar__title {
    flex-grow: 1;
    margin: 0;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .app-bar__title:not(:empty) {
    margin-bottom: calc((var(--app-bar-height-mobile) - (var(--lumo-font-size-l) * var(--lumo-line-height-xs))) / 2);
    margin-top: calc((var(--app-bar-height-mobile) - (var(--lumo-font-size-l) * var(--lumo-line-height-xs))) / 2);
  }

  /* Action items */
  .app-bar__action-items > *:not(:last-child) {
    margin-right: var(--lumo-space-s);
  }

  .app-bar__tab-container {
    padding: 0 var(--lumo-space-m);
  }

  /* Search */
  .app-bar vaadin-text-field {
    padding: 0;
  }

  /* Avatar */
  .app-bar__avatar {
    border-radius: 100%;
    flex-shrink: 0;
    height: var(--lumo-size-s);
    margin-left: var(--lumo-space-m);
    width: var(--lumo-size-s);
  }

  /* Tabs */
  .app-bar__tabs {
    box-shadow: none;
  }

  .app-bar__tab vaadin-button {
    margin: 0 0 0 var(--lumo-space-s);
  }

  .app-bar__add-tab {
    flex-shrink: 0;
  }

  @media (min-width: 1024px) {
    .app-bar__navi-icon {
      display: none;
    }

    .app-bar__context-icon {
      margin-bottom: calc((var(--app-bar-height-desktop) - var(--lumo-icon-size-m)) / 2);
      margin-top: calc((var(--app-bar-height-desktop) - var(--lumo-icon-size-m)) / 2);
    }

    .app-bar__title:not(:empty) {
      margin-bottom: calc((var(--app-bar-height-desktop) - (var(--lumo-font-size-l) * var(--lumo-line-height-xs))) / 2);
      margin-top: calc((var(--app-bar-height-desktop) - (var(--lumo-font-size-l) * var(--lumo-line-height-xs))) / 2);
    }

    .app-bar__tab-container {
      padding: 0 var(--lumo-space-l);
    }
  }

    /* IE 11 workarounds */
    @media all and (-ms-high-contrast: none), (-ms-high-contrast: active) {
      /* IE 11 can't handle different themes for sub-parts of the application, without the parts having own
         shadow roots. Instead of polluting the code for all browsers, the relevant colors of dark theme
         is hard coded here. */
      .app-bar {
        color: hsl(214, 100%, 98%);
        background-color: hsl(214, 35%, 21%);
        box-shadow: 0 2px 4px -1px hsla(214, 8%, 4%, 0.23), 0 3px 12px -1px hsla(214, 12%, 6%, 0.32);
      }

      .app-bar__title {
        color: hsl(214, 100%, 98%);
      }
    }
`)
