import { nodeResolve } from '@rollup/plugin-node-resolve';

export default {
  input: 'www/main.js',
  output: {
    dir: 'www',
    format: 'cjs',
    inlineDynamicImports: true
  },
  plugins: [nodeResolve()],
};