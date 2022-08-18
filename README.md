# @capacitor-community/file-opener

Capacitor File Opener. The plugin is able to open a file given the mimeType and the file uri.

## Install

```bash
npm install @capacitor-community/file-opener
npx cap sync
```

## API

<docgen-index>

* [`open(...)`](#open)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### open(...)

```typescript
open(options: FileOpenerOptions) => Promise<void>
```

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#fileopeneroptions">FileOpenerOptions</a></code> |

--------------------


### Interfaces


#### FileOpenerOptions

| Prop                  | Type                 |
| --------------------- | -------------------- |
| **`filePath`**        | <code>string</code>  |
| **`contentType`**     | <code>string</code>  |
| **`openWithDefault`** | <code>boolean</code> |

</docgen-api>
