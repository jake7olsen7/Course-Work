public class NodeFactNum extends NodeFact {

    private String num;

    public NodeFactNum(String num) {
	this.num=num;
    }

    public int eval(Environment env) throws EvalException {
	return (int) Double.parseDouble(num);
    }

}
