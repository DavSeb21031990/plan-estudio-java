// @ts-check
import {defineConfig} from 'astro/config';
import starlight from '@astrojs/starlight';

// https://astro.build/config
export default defineConfig({

  site: 'https://davseb21031990.github.io/plan-estudio-java',
  base: '/plan-estudio-java/',

  integrations: [
    starlight({
      title: 'Documentación',
      social: [{
        icon: 'github',
        label: 'GitHub',
        href: 'https://github.com/withastro/starlight'
      }],
      sidebar: [
        {
          label: 'Guides',
          items: [
            // Each item here is one entry in the navigation menu.
            {label: 'Guias', slug: 'guias/home'},
            {
              label: 'Java',
              items: [
                {label: 'Introducción', slug: 'guias/java/home'},
                {
                  label: 'Features',
                  items: [
                    {
                      label: 'Record Class',
                      slug: 'guias/java/features/record-class'
                    },
                    {
                      label: 'Text Blocks',
                      slug: 'guias/java/features/text-blocks'
                    },
                    {
                      label: 'Pattern Matching',
                      slug: 'guias/java/features/pattern-matching'
                    },
                    {
                      label: 'Switch Expressions',
                      slug: 'guias/java/features/switch-expressions'
                    },
                  ]
                },
                {
                  label: 'Principios SOLID',
                  items: [
                    {
                      label: 'Single Responsability',
                      slug: 'guias/java/solid/single-responsability'
                    },
                    {
                      label: 'Open - Closed',
                      slug: 'guias/java/solid/open-closed'
                    },
                    {
                      label: 'Liskov Substitution',
                      slug: 'guias/java/solid/liskov-substitution'
                    },
                    {
                      label: 'Interface Segregation',
                      slug: 'guias/java/solid/interface-segregation'
                    },
                    {
                      label: 'Dependency Inversion',
                      slug: 'guias/java/solid/dependency-inversion'
                    },
                  ]
                },
                {
                  label: 'Error Handling',
                  items: [
                    {
                      label: 'Error handling philosophy',
                      slug: 'guias/java/error-handle/error-handling-philosophy'
                    },
                    {
                      label: 'Code Formatting Consistency',
                      slug: 'guias/java/error-handle/code-formatting-consistency'
                    },
                    {
                      label: 'Exception Hierarchy Design Patterns',
                      slug: 'guias/java/error-handle/exception-hierarchy-design-patterns'
                    },
                    {
                      label: 'Logs handling',
                      slug: 'guias/java/error-handle/logs-handling'
                    },
                    {
                      label: 'Error Handling Coverage',
                      slug: 'guias/java/error-handle/error-handling-coverage'
                    },
                    {
                      label: 'Checked vs Unchecked Exceptions Strategy',
                      slug: 'guias/java/error-handle/checked-vs-unchecked-exceptions-strategy'
                    },

                  ]
                },
              ]
            },
          ],
        },
        {
          label: 'Reference',
          autogenerate: {directory: 'reference'},
        },
      ],
    }),
  ],
});
