/*
<<<<<<< HEAD
 * Copyright 2015-2017 the original author or authors.
=======
 * Copyright 2015 the original author or authors.
>>>>>>> 0c39f92... #482 - Add support for Collection+JSON media type
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.hateoas.support;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.core.io.Resource;

/**
 * @author Greg Turnquist
 */
public final class MappingUtils {

	/**
	 * Read test files.
	 *
	 * @param resource as a {@link Resource}
	 * @return
	 * @throws IOException
	 */
	public static String read(Resource resource) throws IOException {

		Scanner scanner = null;

		try {

			scanner = new Scanner(resource.getInputStream());
			StringBuilder builder = new StringBuilder();

			while (scanner.hasNextLine()) {

				builder.append(scanner.nextLine());

				if (scanner.hasNextLine()) {
					builder.append("\n");
				}
			}

			return builder.toString();

		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}
}