// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.codeu.codingchallenge;

import java.io.IOException;

interface JSONParser {

  // PARSE
  //
  // Given a string that should be a valid JSON-lite object encoded as a string
  // return the parsed object. If for any reason the string is found to be
  // invalid, the method should throw an IOException.
  JSON parse(String object) throws IOException;
}
