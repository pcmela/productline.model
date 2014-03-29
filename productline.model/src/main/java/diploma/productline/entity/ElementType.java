package diploma.productline.entity;

public enum ElementType {
	RESOURCES(1), TESTS(2), ANALYSIS(3), DOCUMENTS(4), DIAGRAMS(5), OTHERS(6), UNKNOWN(
			-1);

	private int id;

	private ElementType(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static ElementType get(int id) {
		switch (id) {
		case 1:
			return RESOURCES;
		case 2:
			return TESTS;
		case 3:
			return ANALYSIS;
		case 4:
			return DOCUMENTS;
		case 5:
			return DIAGRAMS;
		case 6:
			return OTHERS;
		default:
			return UNKNOWN;
		}
	}

	public static ElementType get(String name) {
		switch (name) {
		case "Resources":
			return RESOURCES;
		case "Tests":
			return TESTS;
		case "Analysis":
			return ANALYSIS;
		case "Documents":
			return DOCUMENTS;
		case "Diagrams":
			return DIAGRAMS;
		case "Others":
			return OTHERS;
		default:
			return UNKNOWN;
		}
	}
	
	public static Type getType(String name){
		Type t = new Type();
		ElementType et = get(name);
		t.setId(et.getId());
		t.setName(et.toString());
		
		return t;
	}

	public static String[] toArray() {
		ElementType[] types = values();
		String[] array = new String[types.length - 1];

		for (int i = 0; i < types.length - 1; i++) {
			if (types[i] != UNKNOWN) {
				array[i] = types[i].toString();
			}
		}

		return array;
	}

	public String toString() {
		String s = super.toString();
		return s.substring(0, 1) + s.substring(1).toLowerCase();
	};
}
