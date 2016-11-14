package com.brilliancemobility.heroes.net;

/**
 * Created by dongsun on 13/11/16.
 */

public class TestEnum {

	public enum ImageSize {
		/**
		 * large square 150x150
		 */
		LARGE_SQUARE("_q"),
		/**
		 * medium 640, 640 on longest side
		 */
		MEDIUM("_z");

		private String suffix;

		private ImageSize(String suffix) {
			this.suffix = suffix;
		}

		public String getSuffix() {
			return this.suffix;
		}

		@Override
		public String toString() {
			return this.suffix;
		}
	}

	public enum SortOrder {
		RELEVANCE("relevance"),
		DATE_POSTED_DESC("date-posted-desc"),
		DATE_TAKEN_DESC("date-taken-desc");

		private String value;

		private SortOrder(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}
}
