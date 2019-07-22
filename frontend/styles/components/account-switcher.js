import { style } from '../styler.js';

style(`
  .account-switcher__avatar {
    border-radius: 100%;
    display: flex;
    height: var(--lumo-size-l);
    margin: var(--lumo-space-m) 0 var(--lumo-space-l) var(--lumo-space-m);
    width: var(--lumo-size-l);
  }

  .account-switcher__title {
    margin: 0;
    padding: 0 var(--lumo-space-m);
  }

  .account-switcher__email {
    align-items: center;
    box-shadow: inset 0 -1px var(--lumo-contrast-20pct);
    display: flex;
    font-size: var(--lumo-font-size-s);
    justify-content: space-between;
    padding: 0 var(--lumo-space-s) var(--lumo-space-m) var(--lumo-space-m);
  }

  .account-switcher__email vaadin-button {
    margin: 0;
  }
`)
