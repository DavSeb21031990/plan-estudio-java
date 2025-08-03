# Documentación con Astro

```
npm create astro@latest -- --template starlight
```

## 🚀 Estructura del proyecto de documentación

Inside of your Astro + Starlight project, you'll see the following folders and files:

```
.
├── public/
│   ├── .nojekyll
├── src/
│   ├── assets/
│   ├── content/
│   │   └── docs/
│   └── content.config.ts
├── astro.config.mjs
├── package.json
└── tsconfig.json
```

- Los archivos de la documentación estan en formato `.md` o `.mdx` en el directorio `src/content/docs/`.
- Las imagenes se agregan en el directorio `src/assets/`.
- Los recursos estáticos, como favicons pueden ser colocados en el directorio `public/`.
- Se crea el archivo `.nojekull` para que `Github Pages` considere los archivos `_astro`

## 🧞 Comandos

Todos los comandos se ejecutan desde la raíz del proyecto, desde un terminal:

| Command                   | Action                                                                                   |
| :------------------------ |:-----------------------------------------------------------------------------------------|
| `npm install`             | Instalar dependencias                                                                    |
| `npm run dev`             | Inicia el servidor local en `localhost:4321`                                             |
| `npm run build`           | Construye el sitio para el ambiente de producción `./dist/` |
| `npm run preview`         | Previsualice su compilación localmente, antes de desplegarla                                             |
| `npm run astro ...`       | Ejecute comandos CLI como `astro add`, `astro check`                                         |
| `npm run astro -- --help` | Ayuda para utilizar Astro CLI                                                             |
