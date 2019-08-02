import { style } from "../styler.js";

style(`
  .navi-drawer {
    z-index: 2;
  }

  .navi-drawer[open] + * {
    pointer-events: none;
  }

  /* Scrim */
  .navi-drawer__scrim {
    animation: var(--transition-duration-m) lumo-overlay-backdrop-enter both;
    background: var(--lumo-shade-20pct);
    bottom: 0;
    opacity: 0;
    pointer-events: none;
    position: absolute;
    top: 0;
    width: 100%;
    will-change: opacity;
  }

  /* Content */
  .navi-drawer__content {
    background-color: var(--lumo-base-color);
    box-shadow: var(--lumo-box-shadow-s);
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    height: 100%;
    position: relative;
    transition: margin var(--transition-duration-m);
    width: var(--navi-drawer-width);
  }

  /* Search */
  .navi-drawer vaadin-text-field {
    box-shadow: inset 0 -1px var(--lumo-contrast-20pct);
    box-sizing: border-box;
    padding: var(--lumo-space-m);
    width: 100%;
  }

  /* Scrollable area */
  .navi-drawer__scroll-area {
    box-shadow: inset 0 -1px var(--lumo-contrast-20pct);
    flex: 1;
    overflow: auto;
    -webkit-overflow-scrolling: touch;
  }

  /* Footer button */
  .navi-drawer__footer {
    border-radius: 0;
    margin: auto 0 0 0;
    min-width: 0;
  }

  /* Rail navigation */
  .navi-drawer[rail]:not([open]) .navi-drawer__content {
    left: 0;
    overflow: hidden;
    position: absolute;
    top: 0;
    transition: width var(--transition-duration-s);
    width: var(--navi-drawer-rail-width);
  }

  .navi-drawer[rail]:not([open]):hover .navi-drawer__content {
    width: var(--navi-drawer-width);
  }

  /* Push the content in rail mode. */
  .navi-drawer[rail]:not([open]) + .root__column {
    padding-left: var(--navi-drawer-rail-width);
  }


  @media (max-width: 1023px) {
    /* Show scrim when drawer is open */
    .navi-drawer[open] .navi-drawer__scrim {
      opacity: 1;
      pointer-events: all;
    }

    /* Don't push the content in rail mode on narrow viewports. */
    .navi-drawer[rail]:not([open]) + .root__column {
      padding-left: 0;
    }

    /* Fixed positioning on narrow viewports. */
    .navi-drawer__content {
      bottom: 0;
      position: absolute;
      top: 0;
    }

    /* Push the drawer out of view */
    .navi-drawer:not([open]) .navi-drawer__content {
      margin-left: calc(var(--navi-drawer-width) * -1.2);
    }

    /* Hide the footer */
    .navi-drawer__footer {
      display: none;
    }
  }

  @media (min-width: 1024px) {
    .navi-drawer[rail]:not([open]):not(:hover) .account-switcher__avatar,
    .navi-drawer[rail]:not([open]):not(:hover) .navi-item__link iron-icon {
      margin-left: auto;
      margin-right: auto;
    }

    .navi-drawer[rail]:not([open]):not(:hover) .account-switcher__title,
    .navi-drawer[rail]:not([open]):not(:hover) .account-switcher__email,
    .navi-drawer[rail]:not([open]):not(:hover) .brand-expression__title,
    .navi-drawer[rail]:not([open]):not(:hover) .navi-item[level],
    .navi-drawer[rail]:not([open]):not(:hover) .navi-item__link span,
    .navi-drawer[rail]:not([open]):not(:hover) .navi-item vaadin-button {
      display: none;
    }

    .navi-drawer[rail]:not([open]):not(:hover) .navi-drawer__footer iron-icon {
      margin-left: 0.25em;
      margin-right: -0.25em;
      width: var(--navi-drawer-rail-width);
    }
  }

  /* IE 11 workarounds */
  @media all and (-ms-high-contrast: none), (-ms-high-contrast: active) {
    .navi-drawer__scrim,
    .navi-drawer__content {
      /* z-index is needed on each level for IE11 support. For other browsers .navi-drawer z-index is enough */
      z-index: 2
    }


    .brand-expression .h3 {
      font-size: 1.375rem;
    }
  }

  /* Push the drawer out of view */
  @media all and (max-width: 1023px) and (-ms-high-contrast: none), (-ms-high-contrast: active) {
    .navi-drawer:not([open]) .navi-drawer__content {
      /* IE11 doesn't understand nested calc's, so flatting one level */
      margin-left: -18.9rem;
    }
  }
`);
