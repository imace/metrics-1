/**
 * Copyright 2014 Groupon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *     limitations under the License.
 */

fs = require("fs");

module.exports = function (grunt) {
  grunt.loadNpmTasks('grunt-typescript');
  grunt.loadNpmTasks('grunt-mocha-istanbul');
  grunt.loadNpmTasks('grunt-open');

  var mochaReporter = grunt.option('mochaReporter') || 'spec';
  var mochaOptions = [];
  if(grunt.option('vtest')) {
    mochaOptions.push("--verbose=true");
  }
  if(grunt.option('namegrep')) {
    mochaOptions.push("--grep=" + grunt.option('namegrep'));
  }
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    typescript: {
      base: {
        src: ['src/**/*.ts'],
        dest: 'lib/',
        options: {
          module: 'commonjs',
          target: 'es5',
          basePath: 'src',
          comments: true
        }
      }
    },
    mocha_istanbul: {
      coverage: {
        src: 'test', // the folder, not the files,
        noColors: 'true',
        options: {
          reporter: mochaReporter,
          root: './lib',
          check: {
              lines: 97,
              branches: 95,
              statements: 97,
              functions: 97
          },
          mochaOptions: mochaOptions
        }
      }
    },
    open : {
      ccreport : {
        path: './coverage/lcov-report/index.html'
      }
    }
  });

  grunt.registerTask('cover', 'Compile code and run tests with coverage.', ['typescript', 'mocha_istanbul:coverage']);

  grunt.registerTask('test', 'Compile code and run tests with coverage.', ['cover']);

  grunt.registerTask('test-only', 'Run tests without compiling code.', ['mocha_istanbul:coverage']);

  grunt.registerTask('opencc', 'Open code coverage report.', 'open:ccreport');

  grunt.registerTask('build', 'Compile code', ['typescript']);
  grunt.registerTask('default', ['build']);
  grunt.registerTask('', ['build']);
};
