enum Facing{
	N("north"),
	S("south"),
	E("east"),
	W("west"),
	M("towards Michael"),
	L("towards Lucifer");

	private String valueOf;
	private Facing(String f){ valueOf = f; }
	public String valueOf(){ return valueOf; }
}
