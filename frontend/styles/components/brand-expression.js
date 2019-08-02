import { style } from '../styler.js';

style(`
  .brand-expression {
    align-items: center;
    box-shadow: inset 0 -1px var(--lumo-contrast-20pct);
    box-sizing: border-box;
    display: flex;
    /* Application header height with tabs */
    height: calc(var(--app-bar-height) + var(--lumo-size-l));
    justify-content: center;
    padding: var(--lumo-space-m);
  }

  .brand-expression__logo {
    max-height: 100%;
    max-width: 100%;
  }

  .brand-expression__title {
    margin-left: var(--lumo-space-s);
  }

  /* IE 11 workarounds */
  @media all and (-ms-high-contrast: none), (-ms-high-contrast: active) {
    .navi-drawer[rail]:not([open]):not(:hover) .brand-expression__logo {
      /* With max-width, the logo gets too wide */
      width: 100%;
    }
  }
`)
