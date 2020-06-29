package matsu.jippi.pojo.common;

public class FrameEntryPlayerOrFollower {
    private PreFrameUpdateType pre;
    private PostFrameUpdateType post;

    public PreFrameUpdateType getPre() {
        return pre;
    }

    public void setPre(PreFrameUpdateType pre) {
        this.pre = pre;
    }

    public PostFrameUpdateType getPost() {
        return post;
    }

    public void setPost(PostFrameUpdateType post) {
        this.post = post;
    }

    public FrameEntryPlayerOrFollower(PreFrameUpdateType pre, PostFrameUpdateType post) {
        this.pre = pre;
        this.post = post;
    }

    public FrameEntryPlayerOrFollower() {
    }

}
